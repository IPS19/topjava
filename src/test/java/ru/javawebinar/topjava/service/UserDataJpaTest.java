package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.*;

@ActiveProfiles(profiles = {POSTGRES_DB, DATAJPA})
public class UserDataJpaTest extends AbsractUserServiceTest {
    public UserDataJpaTest() {
        super();
    }
}
