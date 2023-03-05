package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.*;

@ActiveProfiles(profiles = {POSTGRES_DB, JPA})
public class UserJpaTest extends AbsractUserServiceTest {
    public UserJpaTest() {
        super();
    }
}
