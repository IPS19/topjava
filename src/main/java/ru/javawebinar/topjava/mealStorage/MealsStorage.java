package ru.javawebinar.topjava.mealStorage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealsStorage {
    List<MealTo> getAll();

    Meal getById(int id);

    void add(Meal meal);

    void delete(int id);

    void update(Meal meal);
}
