package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Profile;
import ru.javawebinar.topjava.repository.JpaUtil;

import static ru.javawebinar.topjava.Profiles.JDBC;
import static ru.javawebinar.topjava.Profiles.JPA;

public abstract class AbstractJpaDatajpaServiceTest extends AbstractUserServiceTest {
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    protected JpaUtil jpaUtil;

    @Before
    @Profile(value = {JPA, JDBC})
    public void setup() {
        cacheManager.getCache("users").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }
}
