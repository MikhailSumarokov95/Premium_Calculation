package ru.sumarokov.premium_calculation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.sumarokov.premium_calculation.config.AbstractApplicationTest;
import ru.sumarokov.premium_calculation.entity.ProductivityLevel;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

public class ProductivityLevelRepositoryTest extends AbstractApplicationTest {

    @Autowired
    private ProductivityLevelRepository productivityLevelRepository;

    @BeforeMethod
    void setUp() {
        productivityLevelRepository.deleteAll();
    }

    @Test
    public void findAllByOrderByPremiumDesc() {
        //given
        ProductivityLevel productivityLevel = new ProductivityLevel();
        productivityLevel.setName("Level 0");
        productivityLevel.setPremium(new BigDecimal(5000));
        productivityLevel.setMinCountCredits(10);
        productivityLevel.setMinSumAmountCredits(new BigDecimal(50000));
        productivityLevel.setMinPenetrationSms(new BigDecimal(75));
        productivityLevel.setMinPenetrationInsurance(new BigDecimal(50));
        productivityLevelRepository.save(productivityLevel);

        //when
        Integer count = productivityLevelRepository.findAll().size();

        //then
        assertEquals(1, count);
    }

    @Test
    public void findAllByOrderByPremiumDes2c() {
        //given
        ProductivityLevel productivityLevel = new ProductivityLevel();
        productivityLevel.setName("Level 0");
        productivityLevel.setPremium(new BigDecimal(5000));
        productivityLevel.setMinCountCredits(10);
        productivityLevel.setMinSumAmountCredits(new BigDecimal(50000));
        productivityLevel.setMinPenetrationSms(new BigDecimal(75));
        productivityLevel.setMinPenetrationInsurance(new BigDecimal(50));
        productivityLevelRepository.save(productivityLevel);

        //when
        Integer count = productivityLevelRepository.findAll().size();

        //then
        assertEquals(1, count);
    }
}
