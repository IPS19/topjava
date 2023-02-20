package ru.javawebinar.topjava;


import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER_ID_MEAL1 = START_SEQ + 3;
    public static final int USER_ID_MEAL2 = START_SEQ + 4;
    public static final int ADMIN_ID_MEAL1 = START_SEQ + 6;
    public static final int ADMIN_ID_MEAL2 = START_SEQ + 7;
    public static final int NOT_FOUND = 100;

    public static final Meal userMeal1 = new Meal(USER_ID_MEAL1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
            "Завтрак user", 500);
    public static final Meal userMeal2 = new Meal(USER_ID_MEAL2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0),
            "Обед user", 1000);
    public static final Meal adminMeal1 = new Meal(ADMIN_ID_MEAL1, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
            "Завтрак admin", 500);
    public static final Meal adminMeal2 = new Meal(ADMIN_ID_MEAL2, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
            "Обед admin", 700);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }

}
