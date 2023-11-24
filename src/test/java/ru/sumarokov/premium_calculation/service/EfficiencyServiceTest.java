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

public class EfficiencyServiceTest extends AbstractApplicationTest {


    @Autowired
    private FurResultService furResultService;
    @Autowired
    private FurResultRepository furResultRepository;
    @Autowired
    private CriteriaBonusForFurRepository criteriaBonusForFurRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductGroupRepository productGroupRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;
    @Autowired
    private CreditRepository creditRepository;
    @Autowired
    private ProductivityLevelRepository productivityLevelRepository;
    @Autowired
    private PremiumLimitRepository premiumLimitRepository;
    @Autowired
    private EfficiencyService efficiencyService;

    @Test
    public void calculateEfficiency() {
        User userCurrent = userRepository.save(new User("userOne", "pass", "emailOne@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        ProductGroup productGroup = productGroupRepository.save(new ProductGroup("Услуги", BigDecimal.valueOf(0.13), BigDecimal.valueOf(30), BigDecimal.valueOf(3000), TypeCredit.POINT_OF_SALE, BigDecimal.ZERO));
        Insurance insurance = insuranceRepository.save(new Insurance("Безработица", BigDecimal.valueOf(100), BigDecimal.valueOf(1.4)));
        Insurance insuranceNot = insuranceRepository.save(new Insurance("Нет", BigDecimal.ZERO, BigDecimal.ZERO));
        criteriaBonusForFurRepository.save(new CriteriaBonusForFur(BigDecimal.valueOf(750000), BigDecimal.valueOf(70), BigDecimal.valueOf(7500)));
        criteriaBonusForFurRepository.save(new CriteriaBonusForFur(BigDecimal.valueOf(1000000), BigDecimal.valueOf(75), BigDecimal.valueOf(17500)));
        premiumLimitRepository.save(new PremiumLimit(BigDecimal.valueOf(100000), true));
        productivityLevelRepository.save(new ProductivityLevel("Уровень 0", BigDecimal.ZERO, 0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
        productivityLevelRepository.save(new ProductivityLevel("Медный", BigDecimal.valueOf(7000), 16, BigDecimal.valueOf(500000), BigDecimal.valueOf(75), BigDecimal.ZERO));
        productivityLevelRepository.save(new ProductivityLevel("Бронзовый", BigDecimal.valueOf(10000), 21, BigDecimal.valueOf(650000), BigDecimal.valueOf(85), BigDecimal.valueOf(30)));
        productivityLevelRepository.save(new ProductivityLevel("Серебрянный", BigDecimal.valueOf(15000), 30, BigDecimal.valueOf(1000000), BigDecimal.valueOf(90), BigDecimal.valueOf(40)));
        productivityLevelRepository.save(new ProductivityLevel("Золотой", BigDecimal.valueOf(20000), 36, BigDecimal.valueOf(1500000), BigDecimal.valueOf(95), BigDecimal.valueOf(60)));
        creditRepository.save(new Credit(productGroup, BigDecimal.valueOf(100000), 12, BigDecimal.valueOf(10), insurance, true, false, false, false, userCurrent));
        creditRepository.save(new Credit(productGroup, BigDecimal.valueOf(200000), 12, BigDecimal.valueOf(10), insurance, true, true, false, false, userCurrent));
        creditRepository.save(new Credit(productGroup, BigDecimal.valueOf(600000), 24, BigDecimal.valueOf(10), insuranceNot, true, true, true, true, userCurrent));

        Efficiency efficiency = efficiencyService.calculateEfficiency(userCurrent);

        Assert.assertEquals(efficiency.getFurBonus().stripTrailingZeros(), BigDecimal.valueOf(7500).stripTrailingZeros());
        Assert.assertEquals(efficiency.getPremiumForCredits().stripTrailingZeros(), BigDecimal.valueOf(1354).stripTrailingZeros());
        Assert.assertEquals(efficiency.getPremiumInsurance().stripTrailingZeros(), BigDecimal.valueOf(4200).stripTrailingZeros());
        Assert.assertEquals(efficiency.getTotalProductivity().stripTrailingZeros(), BigDecimal.valueOf(10000).stripTrailingZeros());
        Assert.assertEquals(efficiency.getTotalPremium().stripTrailingZeros(), BigDecimal.valueOf(23054).stripTrailingZeros());
    }
}
