package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final Meal userMeal1 = new Meal(START_SEQ, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal userMeal2 = new Meal(START_SEQ + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal userMeal3 = new Meal(START_SEQ + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal userMeal4 = new Meal(START_SEQ + 3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal userMeal5 = new Meal(START_SEQ + 4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal userMeal6 = new Meal(START_SEQ + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal userMeal7 = new Meal(START_SEQ + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal adminMeal1 = new Meal(START_SEQ + 7, LocalDateTime.of(2020, Month.FEBRUARY, 20, 10, 0), "Завтрак админа", 410);
    public static final Meal adminMeal2 = new Meal(START_SEQ + 8, LocalDateTime.of(2020, Month.FEBRUARY, 20, 15, 0), "Обед админа", 800);
    public static final Meal adminMeal3 = new Meal(START_SEQ + 9, LocalDateTime.of(2020, Month.FEBRUARY, 20, 20, 0), "Ужин админа", 500);
    public static final List<Meal> userMeals = Arrays.asList(userMeal7, userMeal6, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);
    public static final List<Meal> adminMeals = Arrays.asList(adminMeal3, adminMeal2, adminMeal1);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, 3, 8, 10, 0), "Новая еда", 777);
    }

    public static Meal getUpdated() {
        Meal meal = new Meal(userMeal1);
        meal.setDateTime(LocalDateTime.of(2020, 1, 29, 15, 0));
        meal.setCalories(888);
        meal.setDescription("Updated meal");
        return meal;
    }
}

