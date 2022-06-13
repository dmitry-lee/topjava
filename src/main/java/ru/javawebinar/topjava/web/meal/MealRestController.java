package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public Meal save(Meal meal) {
        return service.save(meal, authUserId());
    }

    public boolean delete(int id) {
        return service.delete(id, authUserId());
    }

    public Meal get(int id) {
        return service.get(id, authUserId());
    }

    public List<Meal> getAll() {
        return service.getAll(authUserId());
    }

    public List<Meal> getAll(LocalDate startDate, LocalDate endDate) {
        return service.getAll(authUserId(), startDate, endDate);
    }
}