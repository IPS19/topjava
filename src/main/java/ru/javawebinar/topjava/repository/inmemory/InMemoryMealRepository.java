package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> this.save(meal, 1));
        MealsUtil.mealsWithUserId2.forEach(mealUserId2 -> this.save(mealUserId2, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            log.info("save id={}", meal.getId());
            return meal;
        } else {        // handle case: update, but not present in storage
            int checkId = meal.getId();
            if (repository.get(checkId).getUserId() == userId) {
                meal.setUserId(userId);
                log.info("update id={}", meal.getId());
                return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
            }
        }
        log.info("not save id={}", meal.getId());
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = ValidationUtil.checkNotFoundWithId(repository.get(id), id);
        return meal.getUserId() == userId && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = ValidationUtil.checkNotFoundWithId(repository.get(id), id);
        return meal.getUserId() == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream().filter(meal -> meal.getUserId().equals(userId))
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}

