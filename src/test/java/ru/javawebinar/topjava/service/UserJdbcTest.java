package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.JDBC;
import static ru.javawebinar.topjava.Profiles.POSTGRES_DB;

@ActiveProfiles(profiles = {POSTGRES_DB, JDBC})
public class UserJdbcTest extends AbsractUserServiceTest {
    public UserJdbcTest() {
        super();
    }
}
