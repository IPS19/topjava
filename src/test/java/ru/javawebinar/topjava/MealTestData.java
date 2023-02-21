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
    public static final int USER_ID_MEAL3 = START_SEQ + 5;
    public static final int ADMIN_ID_MEAL1 = START_SEQ + 6;
    public static final int ADMIN_ID_MEAL2 = START_SEQ + 7;
    public static final int ADMIN_ID_MEAL3 = START_SEQ + 8;

    public static final Meal userMeal1 = new Meal(USER_ID_MEAL1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
            "Завтрак user", 500);
    public static final Meal userMeal2 = new Meal(USER_ID_MEAL2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0),
            "Обед user", 1000);
    public static final Meal userMeal3 = new Meal(USER_ID_MEAL3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0),
            "Ужин user", 1100);

    public static final Meal adminMeal1 = new Meal(ADMIN_ID_MEAL1, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
            "Завтрак admin", 500);
    public static final Meal adminMeal2 = new Meal(ADMIN_ID_MEAL2, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
            "Обед admin", 650);
    public static final Meal adminMeal3 = new Meal(ADMIN_ID_MEAL3, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0),
            "Ужин admin", 800);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2007,Month.JANUARY,1,21,0),"new meal",2500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userMeal1);
        updated.setDateTime(LocalDateTime.of(2007,Month.JANUARY,1,21,0));
        updated.setCalories(400);
        updated.setDescription("updated breakfast");
        return updated;
    }

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
