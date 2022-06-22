package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealTestData {
    public static final Meal userMeal_1 = new Meal(10000, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal userMeal_2 = new Meal(10001, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal userMeal_3 = new Meal(10002, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal userMeal_4 = new Meal(10003, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal userMeal_5 = new Meal(10004, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal userMeal_6 = new Meal(10005, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal userMeal_7 = new Meal(10006, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal adminMeal_1 = new Meal(10007, LocalDateTime.of(2020, Month.FEBRUARY, 20, 10, 0), "Завтрак админа", 410);
    public static final Meal adminMeal_2 = new Meal(10008, LocalDateTime.of(2020, Month.FEBRUARY, 20, 15, 0), "Обед админа", 800);
    public static final Meal adminMeal_3 = new Meal(10009, LocalDateTime.of(2020, Month.FEBRUARY, 20, 20, 0), "Ужин админа", 500);
    public static final List<Meal> userMeals = Arrays.asList(userMeal_7, userMeal_6, userMeal_5, userMeal_4, userMeal_3, userMeal_2, userMeal_1);
    public static final List<Meal> adminMeals = Arrays.asList(adminMeal_3, adminMeal_2, adminMeal_1);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, 3, 8, 10, 0), "Новая еда", 777);
    }

    public static Meal getUpdated() {
        Meal meal = new Meal(userMeal_1);
        meal.setDateTime(LocalDateTime.of(2020, 1, 29, 15, 0));
        meal.setCalories(888);
        meal.setDescription("Updated meal");
        return meal;
    }
}

