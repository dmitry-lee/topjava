package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MemoryMealStorage;
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
        storage = new MemoryMealStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward;
        String action = request.getParameter("action");
        action = action == null ? "" : action;
        switch (action) {
            case "add":
                Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 0);
                request.setAttribute("meal", meal);
                forward = ADD_UPDATE;
                log.debug("add and forward to {}.", forward);
                break;
            case "update":
                request.setAttribute("meal", storage.get(getId(request)));
                forward = ADD_UPDATE;
                log.debug("update and forward to {}.", forward);
                break;
            case "delete":
                storage.delete(getId(request));
                response.sendRedirect("meals");
                log.debug("delete and redirect to meals list");
                return;
            default:
                forward = MEALS_LIST;
                request.setAttribute("meals", MealsUtil.getMealsTo(storage.getAll(), MealsUtil.CALORIES_PER_DAY));
                log.debug("forward to meals list");
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    private int getId(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("id"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String idParam = request.getParameter("id");
        Integer id = idParam.isEmpty() ? null : Integer.parseInt(idParam);
        Meal meal = new Meal(id,
                LocalDateTime.parse(request.getParameter("datetime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (id == null) {
            log.debug("add meal");
            storage.add(meal);
        } else {
            log.debug("update meal id = {}.", meal.getId());
            storage.update(meal);
        }
        response.sendRedirect("meals");
    }
}
