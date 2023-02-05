package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.mealStorage.MemoryMealsStorage;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MealServlet extends HttpServlet {

    private static final String LIST_USER = "/meals";

    private static final MemoryMealsStorage memoryMealsStorage = MemoryMealsStorage.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "";
        String action = req.getParameter("action");

        if (action == null) {
            req.setAttribute("mealsTo", memoryMealsStorage.getAll());
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
            return;
        }
        if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            memoryMealsStorage.delete(id);
            forward = "/meals.jsp";
            req.setAttribute("mealsTo", memoryMealsStorage.getAll());
        } else if (action.equalsIgnoreCase("edit")) {
            forward = "meal.jsp";
            int id = Integer.parseInt(req.getParameter("id"));
            Meal meal = MemoryMealsStorage.getInstance().getById(id);
            req.setAttribute("meal",meal);
        }
        RequestDispatcher view = req.getRequestDispatcher(forward);
        view.forward(req, resp);

    }
}