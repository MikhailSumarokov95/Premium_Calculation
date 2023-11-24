package ru.sumarokov.premium_calculation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.sumarokov.premium_calculation.entity.User;
import ru.sumarokov.premium_calculation.helper.Role;
import ru.sumarokov.premium_calculation.repository.UserRepository;
import ru.sumarokov.premium_calculation.service.AuthService;
import ru.sumarokov.premium_calculation.tools.Tools;

@Configuration
@Profile("test")
public class TestApplicationContext {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean()
    @Scope("singleton")
    @Primary
    public AuthService authServiceTest(@Autowired UserRepository userRepository) {
        return new AuthService(userRepository) {

            private final User user;

            {
                User user = new User();
                user.setUsername("user");
                user.setPassword("pass");
                user.setEmail("user@mail.ru");
                user.setRole(Role.ROLE_CREDIT_SPECIALIST);
                this.user = user;
            }

            public User getUser() {
                return user;
            }
        };
    }

    @Bean()
    public Tools tools() {
        return new Tools();
    }
}