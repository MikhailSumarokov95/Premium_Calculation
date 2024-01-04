package ru.sumarokov.premium_calculation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.BeforeMethod;

public class AbstractApplicationTestWithDeleteAllTableBeforeMethod extends AbstractApplicationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeMethod
    protected void setUp() {
        jdbcTemplate.update("truncate insurance cascade;");
        jdbcTemplate.update("truncate product_group cascade;");
        jdbcTemplate.update("truncate credit cascade;");
        jdbcTemplate.update("truncate preliminary_credit_result cascade;");
        jdbcTemplate.update("truncate efficiency cascade;");
        jdbcTemplate.update("truncate criteria_bonus_for_fur cascade;");
        jdbcTemplate.update("truncate fur_result cascade;");
        jdbcTemplate.update("truncate productivity_level cascade;");
        jdbcTemplate.update("truncate productivity_result cascade;");
        jdbcTemplate.update("truncate premium_limit cascade;");
        jdbcTemplate.update("truncate insurance_result cascade;");
        jdbcTemplate.update("truncate users cascade;");
    }
}
