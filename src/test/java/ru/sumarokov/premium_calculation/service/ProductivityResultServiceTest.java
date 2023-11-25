package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.sumarokov.premium_calculation.config.AbstractApplicationTest;
import ru.sumarokov.premium_calculation.entity.*;
import ru.sumarokov.premium_calculation.helper.Role;
import ru.sumarokov.premium_calculation.helper.TypeCredit;
import ru.sumarokov.premium_calculation.repository.*;

import java.math.BigDecimal;

public class ProductivityResultServiceTest extends AbstractApplicationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductGroupRepository productGroupRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;
    @Autowired
    private CreditRepository creditRepository;
    @Autowired
    private PreliminaryCreditResultRepository preliminaryCreditResultRepository;
    @Autowired
    private ProductivityLevelRepository productivityLevelRepository;
    @Autowired
    private ProductivityResultService productivityResultService;

    @Test
    private void calculateProductivityResult() {
        User userCurrent = userRepository.save(new User("userOne", "pass", "emailOne@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        ProductGroup productGroup = productGroupRepository.save(new ProductGroup("Услуги", BigDecimal.valueOf(0.13), BigDecimal.valueOf(30), BigDecimal.valueOf(3000), TypeCredit.POINT_OF_SALE, BigDecimal.ZERO));
        Insurance insurance = insuranceRepository.save(new Insurance("Безработица", BigDecimal.valueOf(100), BigDecimal.valueOf(1.4)));
        Insurance insuranceNot = insuranceRepository.save(new Insurance("Нет", BigDecimal.ZERO, BigDecimal.ZERO));
        productivityLevelRepository.save(new ProductivityLevel("Уровень 0", BigDecimal.ZERO, 0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, true));
        productivityLevelRepository.save(new ProductivityLevel("Медный", BigDecimal.valueOf(7000), 16, BigDecimal.valueOf(500000), BigDecimal.valueOf(75), BigDecimal.ZERO, false));
        productivityLevelRepository.save(new ProductivityLevel("Бронзовый", BigDecimal.valueOf(10000), 21, BigDecimal.valueOf(650000), BigDecimal.valueOf(85), BigDecimal.valueOf(30), false));
        productivityLevelRepository.save(new ProductivityLevel("Серебрянный", BigDecimal.valueOf(15000), 30, BigDecimal.valueOf(1000000), BigDecimal.valueOf(90), BigDecimal.valueOf(40), false));
        productivityLevelRepository.save(new ProductivityLevel("Золотой", BigDecimal.valueOf(20000), 36, BigDecimal.valueOf(1500000), BigDecimal.valueOf(95), BigDecimal.valueOf(60), false));
        Credit credit1 = creditRepository.save(new Credit(productGroup, BigDecimal.valueOf(100000), 12, BigDecimal.valueOf(10), insurance, true, false, false, false, userCurrent));
        Credit credit2 = creditRepository.save(new Credit(productGroup, BigDecimal.valueOf(200000), 12, BigDecimal.valueOf(10), insurance, true, false, false, false, userCurrent));
        Credit credit3 = creditRepository.save(new Credit(productGroup, BigDecimal.valueOf(300000), 24, BigDecimal.valueOf(10), insuranceNot, true, false, false, false, userCurrent));
        preliminaryCreditResultRepository.save(new PreliminaryCreditResult(BigDecimal.valueOf(1556), BigDecimal.valueOf(156), BigDecimal.valueOf(1400), BigDecimal.valueOf(100000), BigDecimal.valueOf(156), credit1));
        preliminaryCreditResultRepository.save(new PreliminaryCreditResult(BigDecimal.valueOf(3112), BigDecimal.valueOf(312), BigDecimal.valueOf(2800), BigDecimal.valueOf(200000), BigDecimal.valueOf(312), credit2));
        preliminaryCreditResultRepository.save(new PreliminaryCreditResult(BigDecimal.valueOf(936), BigDecimal.valueOf(936), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.valueOf(936), credit3));

        ProductivityResult productivityResult = productivityResultService.calculateProductivityResult(userCurrent);

        Assert.assertEquals(productivityResult.getSumAmountCreditsLevel().getName(), "Медный");
        Assert.assertEquals(productivityResult.getCountCreditsLevel().getName(), "Уровень 0");
        Assert.assertEquals(productivityResult.getInsurancePenetrationLevel().getName(), "Серебрянный");
        Assert.assertEquals(productivityResult.getSmsPenetrationLevel().getName(), "Золотой");
        Assert.assertEquals(productivityResult.getGeneralLevel().getName(), "Медный");
    }
}
