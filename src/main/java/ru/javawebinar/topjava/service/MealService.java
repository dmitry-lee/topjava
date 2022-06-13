package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal save(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public boolean delete(int id, int userId){
        return repository.delete(id, userId);
    }

    public Meal get(int id, int userId){
        return repository.get(id, userId);
    }

    public List<Meal> getAll(int userId){
        return new ArrayList<>(repository.getAll(userId));
    }

    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        return new ArrayList<>(repository.getAll(userId, startDate, endDate));
    }
}