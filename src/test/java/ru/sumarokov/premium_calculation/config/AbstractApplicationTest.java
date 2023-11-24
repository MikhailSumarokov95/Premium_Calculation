package ru.sumarokov.premium_calculation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.*;

public abstract class AbstractApplicationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SpringBootApplication(scanBasePackages = "ru.sumarokov.premium_calculation")
    static class TestApp {
    }

    private static volatile ConfigurableApplicationContext CONTEXT;

    @AfterMethod
    protected void afterMethod() {
    }

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

    @AfterSuite
    final protected void afterSuite() {
        CONTEXT.close();
        CONTEXT = null;
    }

    @BeforeClass
    protected void beforeClass() {
        CONTEXT.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @BeforeSuite
    final protected void beforeSuite() {
        CONTEXT = new SpringApplicationBuilder(TestApp.class)
                .profiles("test", "test-local")
                .web(WebApplicationType.SERVLET)
                .run();
    }
}
