package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final Meal MEAL_1 = new Meal(10000, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_2 = new Meal(10001, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL_3 = new Meal(10002, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL_4 = new Meal(10003, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal MEAL_5 = new Meal(10004, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL_6 = new Meal(10005, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL_7 = new Meal(10006, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal MEAL_8 = new Meal(10007, LocalDateTime.of(2020, Month.FEBRUARY, 20, 10, 0), "Завтрак админа", 410);
    public static final Meal MEAL_9 = new Meal(10008, LocalDateTime.of(2020, Month.FEBRUARY, 20, 15, 0), "Обед админа", 800);
    public static final Meal MEAL_10 = new Meal(10009, LocalDateTime.of(2020, Month.FEBRUARY, 20, 20, 0), "Ужин админа", 500);
    public static final List<Meal> userMeals = Stream.of(MEAL_1, MEAL_2, MEAL_3, MEAL_4, MEAL_5, MEAL_6, MEAL_7)
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());
    public static final List<Meal> adminMeals = Stream.of(MEAL_8, MEAL_9, MEAL_10)
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, 3, 8, 10, 0), "Новая еда", 777);
    }

    public static Meal getUpdated() {
        Meal meal = new Meal(MEAL_1);
        meal.setDateTime(LocalDateTime.of(2020, 1, 29, 15, 0));
        meal.setCalories(888);
        meal.setDescription("Updated meal");
        return meal;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}

