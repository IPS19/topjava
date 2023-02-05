package ru.javawebinar.topjava.mealStorage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.UserServlet;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.slf4j.LoggerFactory.getLogger;

public class MemoryMealsStorage implements MealsStorage {

    private static final Logger log = getLogger(UserServlet.class);

    private static final MemoryMealsStorage INSTANCE = new MemoryMealsStorage();

    static List<Meal> meals;

    static Integer id = 1;

    private final int CALORIES_PER_DAY = 2000;

    static {
        meals = Stream.of(
                        new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                        new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                        new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410))
                .peek(meal -> meal.setId(id++))
                .collect(Collectors.toList());
    }

    private MemoryMealsStorage() {
    }

    public static MemoryMealsStorage getInstance() {
        return INSTANCE;
    }


    @Override
    public List<MealTo> getAll() {
        return MealsUtil.filteredByStreams(meals,
                LocalTime.MIDNIGHT, LocalTime.of(23, 59, 59, 59), CALORIES_PER_DAY);
    }

    @Override
    public Meal getById(int id) {
        for (Meal meal : meals) {
            if (meal.getId().equals(id))
                return meal;
        }
        return null;
    }

    @Override
    public void add(Meal meal) {
        meal.setId(id++);
        meals.add(meal);
    }

    @Override
    public void delete(int id) {

        for (int i = 0; i < meals.size(); i++) {
            Meal meal = meals.get(i);
            if (meal.getId() == id) {
                log.debug("size before"+meals.size());
                meals.remove(meal);
                log.debug("size after"+meals.size());
            }
        }
    }

    @Override
    public void update(Meal m) {
        for (Meal meal : meals)
            if (meal.getId().equals(m.getId())) {
                meals.remove(meal);
                m.setId(id++);
                meals.add(m);
            }
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public static int getId() {
        return id;
    }

/*    public static void main(String[] args) {
        System.out.println(MemoryMealsStorage.getInstance().getAll());
    }*/

}
