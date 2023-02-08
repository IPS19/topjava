package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.mealStorage.MealsStorage;
import ru.javawebinar.topjava.mealStorage.MemoryMealsStorage;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private final MealsStorage mealsStorage = new MemoryMealsStorage();

    private static final Logger log = getLogger(MealServlet.class);


    private List<MealTo> getMealsTo(List<Meal> meals) {
        return MealsUtil.filteredByStreams(meals,
                LocalTime.MIDNIGHT, LocalTime.of(23, 59, 59, 59), User.getCaloriesPerDay()
        );
    }

    private int parseId(HttpServletRequest req) {
        return Integer.parseInt(req.getParameter("id"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "";
        String action = req.getParameter("action");

        if (action == null) {
            req.setAttribute("mealsTo", getMealsTo(mealsStorage.getAll()));
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
            return;
        }

        switch (action) {
            case "delete":
                int id = parseId(req);
                log.debug("delete- id: " + id);
                mealsStorage.delete(id);
                forward = "/meals.jsp";
                req.setAttribute("mealsTo", getMealsTo(mealsStorage.getAll()));
                //resp.sendRedirect("meals");
                break;

            case "edit":
                forward = "/meal.jsp";
                log.debug("edit element with id: " +parseId(req));
                req.setAttribute("meal", mealsStorage.getById(parseId(req)));
                break;

            case "add":
                forward = "/meal.jsp";
                log.debug("add meal ");

                req.setAttribute("meal", new Meal(LocalDateTime.now(), "", 0));
                break;

            default:
                forward = "/meals.jsp";
                req.setAttribute("mealsTo", getMealsTo(mealsStorage.getAll()));
        }
        RequestDispatcher view = req.getRequestDispatcher(forward);
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"), TimeUtil.formatterForParse);
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        String id = req.getParameter("id");
        Meal meal = new Meal(dateTime, description, calories);
        if (id.equals("")) {
            mealsStorage.add(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            mealsStorage.update(meal);
        }
        req.setAttribute("mealsTo", getMealsTo(mealsStorage.getAll()));
        RequestDispatcher view = req.getRequestDispatcher("/meals.jsp");
        view.forward(req, resp);
    }
}