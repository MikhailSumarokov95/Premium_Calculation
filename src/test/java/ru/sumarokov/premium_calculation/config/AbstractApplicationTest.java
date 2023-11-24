package ru.sumarokov.premium_calculation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.testng.annotations.*;
import ru.sumarokov.premium_calculation.tools.Tools;

public abstract class AbstractApplicationTest {

    @Autowired
    private Tools tools;

    @SpringBootApplication(scanBasePackages = "ru.sumarokov.premium_calculation")
    static class TestApp {
    }

    private static volatile ConfigurableApplicationContext CONTEXT;

    @AfterMethod
    protected void afterMethod() {
    }

    @BeforeMethod
    protected void setUp() {
        tools.deleteDataDB();
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
