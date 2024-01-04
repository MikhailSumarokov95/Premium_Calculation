package ru.sumarokov.premium_calculation.config;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public abstract class AbstractApplicationTest {

    @SpringBootApplication(scanBasePackages = "ru.sumarokov.premium_calculation")
    static class TestApp {
    }

    private static volatile ConfigurableApplicationContext CONTEXT;

    @AfterMethod
    protected void afterMethod() {
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
