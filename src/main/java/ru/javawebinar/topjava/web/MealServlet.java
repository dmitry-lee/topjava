package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MealMapStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealIdCounter;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String MEALS_LIST = "meals.jsp";
    private static final String ADD_UPDATE = "meal.jsp";

    private Storage storage;

    @Override
    public void init() {
        storage = new MealMapStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        if (action == null) {
            forward = MEALS_LIST;
            request.setAttribute("meals", getMealsTo(storage.getAll()));
            request.getRequestDispatcher(forward).forward(request, response);
            return;
        }
        String paramId = request.getParameter("id");
        Integer id = paramId == null ? MealIdCounter.next() : Integer.parseInt(paramId);
        switch (action) {
            case "add":
                Meal meal = new Meal(id);
                request.setAttribute("meal", meal);
                forward = ADD_UPDATE;
                break;
            case "update":
                request.setAttribute("meal", storage.get(id));
                forward = ADD_UPDATE;
                break;
            case "delete":
                storage.delete(id);
                response.sendRedirect("meals");
                return;
            default:
                throw new IllegalStateException("Action " + action + " not valid.");
        }
        request.setAttribute("action", action);
        request.getRequestDispatcher(forward).forward(request, response);
    }

    private List<MealTo> getMealsTo(List<Meal> meals) {
        return MealsUtil.getMealsTo(meals, MealsUtil.CALORIES_PER_DAY);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer id = Integer.parseInt(request.getParameter("id"));
        String action = request.getParameter("action");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("datetime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal;
        switch (action) {
            case "add" :
                meal = new Meal(id, dateTime, description, calories);
                storage.save(meal);
                break;
            case "update":
                meal = storage.get(id);
                meal.setDateTime(dateTime);
                meal.setDescription(description);
                meal.setCalories(calories);
                storage.update(meal);
                break;
        }
        response.sendRedirect("meals");
    }
}
