package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.mealStorage.MemoryMealsStorage;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

public class MealServlet extends HttpServlet {
    private static final MemoryMealsStorage memoryMealsStorage = MemoryMealsStorage.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "";
        String action = req.getParameter("action");

        if (action == null) {
            req.setAttribute("mealsTo", memoryMealsStorage.getAll()); //https://metanit.com/java/javaee/3.8.php
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
            return;
        }
        if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            memoryMealsStorage.delete(id);
            forward = "/meals.jsp";
            req.setAttribute("mealsTo", memoryMealsStorage.getAll());
        } else if (action.equalsIgnoreCase("edit")) {
            forward = "/meal.jsp";
            int id = Integer.parseInt(req.getParameter("id"));
            Meal meal = MemoryMealsStorage.getInstance().getById(id);
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
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"), TimeUtil.formatter);
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        String id = req.getParameter("id");
        Meal meal = new Meal(dateTime, description, calories);
        if (id.equals("")) {
            MemoryMealsStorage.getInstance().add(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            MemoryMealsStorage.getInstance().update(meal);
        }
        req.setAttribute("mealsTo", memoryMealsStorage.getAll());
        RequestDispatcher view = req.getRequestDispatcher("/meals.jsp");
        view.forward(req, resp);
    }
}