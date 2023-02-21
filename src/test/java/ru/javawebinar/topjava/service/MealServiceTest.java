package ru.javawebinar.topjava.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
//        "classpath:spring/spring-app-inMemory.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_ID_MEAL1, USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void delete() {
        service.delete(USER_ID_MEAL1, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID_MEAL1, USER_ID));
    }

    @Test
    @Ignore
    public void getBetweenInclusive() {
        List<Meal> meals = service.getBetweenInclusive(LocalDate.of(2020, 1, 30),
                LocalDate.of(2020, 1, 31), USER_ID);
        assertMatch(meals, userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(ADMIN_ID);
        assertMatch(all, adminMeal3, adminMeal2, adminMeal1);
    }

    @Test
    public void update() {
        Meal updated = new Meal(getUpdated());
        service.update(updated, SecurityUtil.authUserId());
        assertMatch(service.get(userMeal1.getId(), SecurityUtil.authUserId()), getUpdated());

    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), SecurityUtil.authUserId());
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, SecurityUtil.authUserId()), created);
    }

    @Test
    public void deleteAnotherS() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_ID_MEAL1, ADMIN_ID));
    }

    @Test
    public void getAnotherS() {
        assertThrows(NotFoundException.class, () -> service.get(ADMIN_ID_MEAL1, USER_ID));
    }

    @Test
    public void updateAnotherS() {
        assertThrows(NotFoundException.class, () -> service.get(ADMIN_ID_MEAL1, USER_ID));
    }

    @Test
    public void duplicateDateTimeCreate() {
        Meal duplDateTimeMeal = getNew();
        duplDateTimeMeal.setDateTime(userMeal1.getDateTime());
        assertThrows(DuplicateKeyException.class, () -> service.create(duplDateTimeMeal, SecurityUtil.authUserId()));
    }
}