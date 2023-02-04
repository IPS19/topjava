package ru.javawebinar.topjava.mealStorage;

import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealsStorage {
    List<MealTo> getAllMealsTo();
}
