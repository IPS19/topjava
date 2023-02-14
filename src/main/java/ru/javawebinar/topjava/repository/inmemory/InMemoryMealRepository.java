package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> this.save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userID) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserID(userID);
            repository.put(meal.getId(), meal);
            return meal;
        } else
            // handle case: update, but not present in storage
            if (meal.getUserID() == userID) {
                return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
            }
        return null;
    }

    @Override
    public boolean delete(int id, int userID) {
        if (repository.get(id).getUserID() == userID) {
            return repository.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userID) {
        Meal meal = repository.get(id);
        if (meal.getUserID() == userID) {
            return meal;
        }
        return null;

    }

    @Override
    public Collection<Meal> getAll(int userID) {
        return repository.values().stream().filter(meal -> meal.getUserID().equals(userID))
                .sorted((o1, o2) -> {
                    if (o1.getDate().isAfter(o2.getDate())) {
                        return 1;
                    } else if (o1.getDate().equals(o2.getDate())) {
                        return 0;
                    } else return -1;
                }).collect(Collectors.toList());
    }
}

