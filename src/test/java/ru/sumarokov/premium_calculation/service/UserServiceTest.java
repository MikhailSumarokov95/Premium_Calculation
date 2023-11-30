package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.sumarokov.premium_calculation.config.AbstractApplicationTest;
import ru.sumarokov.premium_calculation.entity.User;
import ru.sumarokov.premium_calculation.helper.Role;
import ru.sumarokov.premium_calculation.repository.UserRepository;

public class UserServiceTest extends AbstractApplicationTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void loadUserByUsername() {
        User userCurrent = userRepository.save(new User("userOne", "pass", "emailOne@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        userRepository.save(new User("userTwo", "pass", "emailTwo@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        userRepository.save(new User("userThree", "pass", "emailThree@mail.ru", Role.ROLE_CREDIT_SPECIALIST));

        User user = (User) userService.loadUserByUsername(userCurrent.getUsername());

        Assert.assertEquals(user.getId(), userCurrent.getId());
        Assert.assertEquals(user.getUsername(), "userOne");
        Assert.assertEquals(user.getRole(), Role.ROLE_CREDIT_SPECIALIST);
        Assert.assertEquals(user.getEmail(), "emailOne@mail.ru");
    }

    @Test
    public void loadUserByUsernameNotFoundException() {
        userRepository.save(new User("userOne", "pass", "emailOne@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        userRepository.save(new User("userTwo", "pass", "emailTwo@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        userRepository.save(new User("userThree", "pass", "emailThree@mail.ru", Role.ROLE_CREDIT_SPECIALIST));

        Assert.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("Misha"));
    }

    @Test
    public void createNewUser() {
        userService.createNewUser(new User("userOne", "pass", "emailOne@mail.ru", Role.ROLE_CREDIT_SPECIALIST));

        User user = userRepository.findAll().get(0);

        Assert.assertEquals(user.getUsername(), "userOne");
        Assert.assertEquals(user.getRole(), Role.ROLE_CREDIT_SPECIALIST);
        Assert.assertEquals(user.getEmail(), "emailOne@mail.ru");
    }
}
