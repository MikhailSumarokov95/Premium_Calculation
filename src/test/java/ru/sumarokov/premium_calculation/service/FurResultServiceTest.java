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

public class FurResultServiceTest extends AbstractApplicationTest {


    @Autowired
    private FurResultService furResultService;
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


    @Test
    public void calculateFurResult() {
        User userCurrent = userRepository.save(new User("userOne", "pass", "emailOne@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        ProductGroup productGroup = productGroupRepository.save(new ProductGroup("Услуги", BigDecimal.valueOf(0.13), BigDecimal.valueOf(30), BigDecimal.valueOf(3000), TypeCredit.POINT_OF_SALE, BigDecimal.ZERO));
        Insurance insurance = insuranceRepository.save(new Insurance("Безработица", BigDecimal.valueOf(100), BigDecimal.valueOf(1.4)));
        creditRepository.save(new Credit(productGroup, BigDecimal.valueOf(100000), 12, BigDecimal.valueOf(10), insurance, true, false, false, false, userCurrent));
        creditRepository.save(new Credit(productGroup, BigDecimal.valueOf(600000), 12, BigDecimal.valueOf(10), insurance, true, true, false, false, userCurrent));
        creditRepository.save(new Credit(productGroup, BigDecimal.valueOf(300000), 24, BigDecimal.valueOf(10), insurance, true, true, false, false, userCurrent));
        criteriaBonusForFurRepository.save(new CriteriaBonusForFur(BigDecimal.valueOf(750000), BigDecimal.valueOf(70), BigDecimal.valueOf(7500)));
        criteriaBonusForFurRepository.save(new CriteriaBonusForFur(BigDecimal.valueOf(1000000), BigDecimal.valueOf(75), BigDecimal.valueOf(17500)));

        FurResult furResult = furResultService.calculateFurResult(userCurrent);

        Assert.assertEquals(furResult.getCountCreditsCategoryFur(), 2L);
        Assert.assertEquals(furResult.getCountCreditsCategoryFurWithSms(), 2L);
        Assert.assertEquals(furResult.getSumAmountCreditsCategoryFur().stripTrailingZeros(), BigDecimal.valueOf(900000).stripTrailingZeros());
        Assert.assertEquals(furResult.getPenetrationSmsCreditsCategoryFur().stripTrailingZeros(), BigDecimal.valueOf(100).stripTrailingZeros());
        Assert.assertEquals(furResult.getBonus().stripTrailingZeros(), BigDecimal.valueOf(7500).stripTrailingZeros());
    }
}
