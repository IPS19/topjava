package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
@Profile("HSQLDB")
public class HsqldbMealJdbcRepository extends AbstractJdbcMealRepository {
    public HsqldbMealJdbcRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    Timestamp convertToIdentifiedDateTime(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }

    @Override
    Timestamp addDateTime(Meal meal) {
        return Timestamp.valueOf(meal.getDateTime());
    }

}