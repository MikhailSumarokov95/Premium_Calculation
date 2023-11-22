package ru.sumarokov.premium_calculation;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.testng.annotations.Test;

public class TestTest {

    @Test
    void testPassword() {
        System.out.println(new BCryptPasswordEncoder().encode("123"));
    }
}
