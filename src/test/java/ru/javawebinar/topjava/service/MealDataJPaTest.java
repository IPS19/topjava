package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.*;

@ActiveProfiles(profiles = {POSTGRES_DB,DATAJPA})
public class MealDataJPaTest extends AbstractMealServiceTest {

    public MealDataJPaTest() {
    super();
    }
}
