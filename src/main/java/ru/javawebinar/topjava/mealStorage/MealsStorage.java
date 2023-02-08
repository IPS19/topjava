package ru.javawebinar.topjava.mealStorage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealsStorage {
    List<Meal> getAll();

    Meal getById(int id);

    Meal add(Meal meal);

    void delete(int id);

    Meal update(Meal meal);
}
