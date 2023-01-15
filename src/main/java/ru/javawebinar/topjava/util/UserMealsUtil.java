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
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));

    }

    public static List<UserMealWithExcess> getMealsWithExcess(List<UserMeal> meals, int caloriesPerDay) {
        List<UserMealWithExcess> resultList = new ArrayList<>();
        List<UserMealWithExcess> dailyList = new ArrayList<>();
        LocalDate singleDay = null;

        int sumCalories = 0;
        for (int i = 0; i < meals.size(); i++) {
            UserMeal meal = meals.get(i);
            if (singleDay == null) {
                singleDay = meal.getDateTime().toLocalDate();
                dailyList.add(meal.convertToMealWithExcess());
                sumCalories += meal.getCalories(); //суммируем калории

            } else if (singleDay.equals(meal.getDateTime().toLocalDate())) {
                dailyList.add(meal.convertToMealWithExcess());
                sumCalories += meal.getCalories();
                if (meals.size() == i + 1 && (sumCalories > caloriesPerDay)) {
                    resultList.addAll(dailyList);
                }
            } else {
                if (sumCalories > caloriesPerDay) {
                    resultList.addAll(dailyList);
                }
                dailyList.clear();
                singleDay = meal.getDateTime().toLocalDate();
                dailyList.add(meal.convertToMealWithExcess());
                sumCalories = meal.getCalories();
            }
        }
        return resultList;
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExcess> mealsWithExcess = getMealsWithExcess(meals, caloriesPerDay);

        List<UserMealWithExcess> mealsInInterval = new ArrayList<>();
        for (UserMealWithExcess meal : mealsWithExcess) {
            LocalTime mealTime = meal.getDateTime().toLocalTime();

            if (TimeUtil.isBetweenHalfOpen(mealTime, startTime, endTime)) {
                mealsInInterval.add(meal);
            }
        }
        return mealsInInterval;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, List<UserMeal>> groupedMealsByDate = meals.stream().collect(Collectors.groupingBy(UserMeal::getLocalDate));

        return groupedMealsByDate.values()
                .stream()
                .filter(x -> (x.stream().mapToInt(UserMeal::getCalories)).sum() > caloriesPerDay).flatMap(Collection::stream).collect(Collectors.toList())
                .stream().filter(x -> {
                    LocalTime mealTime = x.getDateTime().toLocalTime();
                    return mealTime.isAfter(startTime) && mealTime.isBefore(endTime);
                })
                .collect(Collectors.toList())
                .stream().map(UserMeal::convertToMealWithExcess).collect(Collectors.toList());
    }
}
