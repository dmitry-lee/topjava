package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration("classpath:spring/spring-db.xml")
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void get() {
        Meal meal = mealService.get(10000, USER_ID);
        assertThat(meal).usingRecursiveComparison().isEqualTo(MEAL_1);
    }

    @Test
    public void getWithForeignUserId() {
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL_1.getId(), ADMIN_ID));
    }

    @Test
    public void delete() {
        mealService.delete(MEAL_1.getId(), USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL_1.getId(), USER_ID));
    }

    @Test
    public void deleteWithForeignUserId() {
        assertThrows(NotFoundException.class, () -> mealService.delete(MEAL_1.getId(), ADMIN_ID));
    }

    @Test
    public void getAll() {
        assertMatch(mealService.getAll(USER_ID), userMeals);
        assertMatch(mealService.getAll(ADMIN_ID), adminMeals);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        mealService.update(updated, USER_ID);
        assertMatch(mealService.get(updated.getId(), USER_ID), getUpdated());
    }

    @Test
    public void updateWithForeignUserId() {
        Meal updated = getUpdated();
        assertThrows(NotFoundException.class, () -> mealService.update(updated, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = mealService.create(getNew(), USER_ID);
        Meal newMeal = getNew();
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(mealService.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () -> mealService.create(new Meal(
                LocalDateTime.of(2020, 1, 30, 20, 0),
                "description", 500), USER_ID));
    }
}