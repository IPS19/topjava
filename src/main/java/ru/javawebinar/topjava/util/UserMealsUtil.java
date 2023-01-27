package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000)
        );

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> dateSumCalories = new HashMap<>();
        for (UserMeal meal : meals) {
            LocalDate day = meal.getLocalDate();
            dateSumCalories.put(day, dateSumCalories.getOrDefault(day, 0) + meal.getCalories());
        }

        List<UserMealWithExcess> resultList = new ArrayList<>();
        for (UserMeal meal : meals) {
            boolean isExcess = dateSumCalories.get(meal.getLocalDate()) > caloriesPerDay;
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                resultList.add(convertToMealWithExcess(meal, isExcess));
        }
        return resultList;
    }

    private static UserMealWithExcess convertToMealWithExcess(UserMeal meal, boolean isExcess) {
        return new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), isExcess);
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dateSumCalories = new HashMap<>();
        return meals.stream()
                .peek(meal -> {
                    LocalDate date = meal.getLocalDate();

                    dateSumCalories.merge(date, meal.getCalories(), Integer::sum);
                    System.out.println(dateSumCalories);
                }).filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal ->
                        convertToMealWithExcess(meal,
                                (dateSumCalories.getOrDefault(meal.getLocalDate(), 0) > caloriesPerDay)))
                .collect(Collectors.toList());
    }
}