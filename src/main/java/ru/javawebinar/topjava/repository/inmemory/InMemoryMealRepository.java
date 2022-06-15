package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return mealBelongsToUser(meal.getId(), userId) ? repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal) : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        if (mealBelongsToUser(id, userId)) {
            return repository.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getId().equals(id) && meal.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAllFilteredByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        return getAllFilteredByPredicate(userId, meal -> DateTimeUtil.isBetweenClosed(meal.getDate(), startDate, endDate));
    }

    public List<Meal> getAllFilteredByPredicate(int userId, Predicate<Meal> filter) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    private boolean mealBelongsToUser(int id, int userId) {
        Meal meal = repository.get(id);
        return meal != null && meal.getUserId().equals(userId);
    }
}

