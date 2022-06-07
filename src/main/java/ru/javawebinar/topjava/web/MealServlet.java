package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MapMealStorage;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String MEALS_LIST = "meals.jsp";
    private static final String ADD_UPDATE = "meal.jsp";

    private MealStorage storage;

    @Override
    public void init() {
        storage = new MapMealStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward;
        String action = request.getParameter("action");
        if (action == null) {
            forward = MEALS_LIST;
            request.setAttribute("meals", MealsUtil.getMealsTo(storage.getAll()));
            request.getRequestDispatcher(forward).forward(request, response);
            log.debug("forward to " + forward);
            return;
        }

        switch (action) {
            case "add":
                Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 0);
                request.setAttribute("meal", meal);
                forward = ADD_UPDATE;
                log.debug("add and forward to " + forward);
                break;
            case "update":
                request.setAttribute("meal", storage.get(Integer.parseInt(request.getParameter("id"))));
                forward = ADD_UPDATE;
                log.debug("update and forward to " + forward);
                break;
            case "delete":
                storage.delete(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect("meals");
                log.debug("delete and redirect to meals list");
                return;
            default:
                forward = MEALS_LIST;
        }
        request.setAttribute("action", action);
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String idParam = request.getParameter("id");
        Integer id = idParam.isEmpty() ? null : Integer.parseInt(idParam);
        String action = request.getParameter("action");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("datetime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(id, dateTime, description, calories);
        switch (action) {
            case "add":
                storage.add(meal);
                log.debug("add meal");
                break;
            case "update":
                storage.update(meal);
                log.debug("update meal id = " + meal.getId());
                break;
        }
        response.sendRedirect("meals");
    }
}
