package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.AbstractControllerTest;

class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + "/";

    @Autowired
    private MealService mealService;

    @Test
    void get() {

    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
    }

    @Test
    void createWithLocation() {
    }

    @Test
    void update() {
    }

    @Test
    void getBetween() {
    }
}