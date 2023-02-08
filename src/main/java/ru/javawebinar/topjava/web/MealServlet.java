package ru.javawebinar.topjava.web;

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

public class MealServlet extends HttpServlet {
    private final MealsStorage mealsStorage = new MemoryMealsStorage();

    private List<MealTo> getMealsTo(List<Meal> meals) {
        return MealsUtil.filteredByStreams(meals,
                LocalTime.MIDNIGHT, LocalTime.of(23, 59, 59, 59), User.getCaloriesPerDay()
        );
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
        if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            mealsStorage.delete(id);
            forward = "/meals.jsp";
            req.setAttribute("mealsTo", getMealsTo(mealsStorage.getAll()));
        } else if (action.equalsIgnoreCase("edit")) {
            forward = "/meal.jsp";
            int id = Integer.parseInt(req.getParameter("id"));
            Meal meal = mealsStorage.getById(id);
            req.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("add")) {
            forward = "/meal.jsp";
            Meal meal = new Meal(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0),
                    "", 0);
            req.setAttribute("meal", meal);
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