package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.*;

@ActiveProfiles(profiles = {POSTGRES_DB, JPA})
public class MealJPaTest extends AbstractMealServiceTest {

    public MealJPaTest() {
        super();
    }
}
