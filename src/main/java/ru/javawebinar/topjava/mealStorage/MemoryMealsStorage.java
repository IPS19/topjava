package ru.javawebinar.topjava.mealStorage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MemoryMealsStorage implements MealsStorage {

    static List<Meal> meals;

    private Map<Integer, MealTo> mealStorage = new HashMap<>();

    static Integer id = 0;

    private final int CALORIES_PER_DAY = 2000;

    static {
        meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }


    @Override
    public List<MealTo> getAllMealsTo() {
        return MealsUtil.filteredByStreams(meals,
                        LocalTime.MIDNIGHT, LocalTime.of(23, 59, 59, 59), CALORIES_PER_DAY)
                .stream().peek(mealTo -> mealTo.setId(++id)).collect(Collectors.toList());
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public static int getId() {
        return id;
    }

    public static void main(String[] args) {
        System.out.println(new MemoryMealsStorage().getAllMealsTo());
    }
}
