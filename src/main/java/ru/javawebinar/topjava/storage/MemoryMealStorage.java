package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealStorage implements MealStorage {

    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger();

    public MemoryMealStorage() {
        MealsUtil.getTestMeals().forEach(this::add);
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(idCounter.incrementAndGet());
        synchronized (this) {
            storage.put(meal.getId(), meal);
            return storage.get(meal.getId());
        }
    }

    @Override
    public Meal get(int id) {
        return storage.get(id);
    }

    @Override
    public synchronized Meal update(Meal meal) {
        storage.replace(meal.getId(), meal);
        return storage.get(meal.getId());
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }
}
