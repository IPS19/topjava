package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

@Repository
@Profile("postgres")
public class PostgresMealJdbcRepository extends AbstractJdbcMealRepository {
    public PostgresMealJdbcRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    LocalDateTime convertToIdentifiedDateTime(LocalDateTime dateTime) {
        return dateTime;
    }

    @Override
    LocalDateTime addDateTime(Meal meal) {
        return meal.getDateTime();
    }
}
