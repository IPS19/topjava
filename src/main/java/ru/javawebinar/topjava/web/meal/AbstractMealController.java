package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

public abstract class AbstractMealController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected MealService service;

    protected int userId = SecurityUtil.authUserId();
}
