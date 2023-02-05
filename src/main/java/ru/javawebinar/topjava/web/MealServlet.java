package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.mealStorage.MemoryMealsStorage;

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
        String action = req.getParameter("action");

        if (action == null) {
            req.setAttribute("mealsTo", memoryMealsStorage.getAll());
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
            return;
        }
        if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            memoryMealsStorage.delete(id);
        }
        RequestDispatcher view = req.getRequestDispatcher("meals.jsp");
        view.forward(req, resp);

    }
}