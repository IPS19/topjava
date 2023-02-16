package ru.javawebinar.topjava.repository.inmemory;

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
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> this.save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        } else {        // handle case: update, but not present in storage
            int checkId = meal.getId();
            if (repository.get(checkId).getUserId() == userId) {
                meal.setUserId(userId);
                return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
            }
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = ValidationUtil.checkNotFoundWithId(repository.get(id), id);
        return meal.getUserId() == userId && repository.remove(id) != null;
/*        if (repository.get(id).getUserId() == userId) {
            return repository.remove(id) != null;
        }
        return false;*/
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = ValidationUtil.checkNotFoundWithId(repository.get(id), id);
        return meal.getUserId() == userId ? meal : null;
/*        Meal meal = repository.get(id);
        if (meal != null) {
            return meal.getUserId() == userId ? meal : null;
        }
        return null;*/
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream().filter(meal -> meal.getUserId().equals(userId))
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());

        /*                .sorted(new Comparator<Meal>() {
                    @Override
                    public int compare(Meal o1, Meal o2) {
                         return o1.getDate().compareTo(o2.getDate());
                    }
                }.reversed())
                .collect(Collectors.toList());*/

/*                .sorted((o1, o2) -> {
                    if (o1.getDate().isAfter(o2.getDate())) {
                        return 1;
                    } else if (o1.getDate().equals(o2.getDate())) {
                        return 0;
                    } else return -1;
                }).collect(Collectors.toList());*/
    }
}

