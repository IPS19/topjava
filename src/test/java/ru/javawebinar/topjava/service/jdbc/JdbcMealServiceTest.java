package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.Profiles.*;

@ActiveProfiles(profiles = {POSTGRES_DB, JDBC})
public class JdbcMealServiceTest extends AbstractMealServiceTest {
}
