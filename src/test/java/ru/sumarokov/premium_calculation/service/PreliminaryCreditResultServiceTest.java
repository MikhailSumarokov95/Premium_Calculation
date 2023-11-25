package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.sumarokov.premium_calculation.config.AbstractApplicationTest;
import ru.sumarokov.premium_calculation.entity.*;
import ru.sumarokov.premium_calculation.helper.Role;
import ru.sumarokov.premium_calculation.helper.TypeCredit;
import ru.sumarokov.premium_calculation.repository.CreditRepository;
import ru.sumarokov.premium_calculation.repository.InsuranceRepository;
import ru.sumarokov.premium_calculation.repository.ProductGroupRepository;
import ru.sumarokov.premium_calculation.repository.UserRepository;

import java.math.BigDecimal;

public class PreliminaryCreditResultServiceTest extends AbstractApplicationTest {

    @Autowired
    private PreliminaryCreditResultService preliminaryCreditResultService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductGroupRepository productGroupRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;
    @Autowired
    private CreditRepository creditRepository;

    @Test
    public void calculatePreliminaryCreditResultProductTypePOINT_OF_SALE() {
        User userCurrent = userRepository.save(new User("userOne", "pass", "emailOne@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        ProductGroup productGroup = productGroupRepository.save(new ProductGroup("Услуги", BigDecimal.valueOf(0.13), BigDecimal.valueOf(30), BigDecimal.valueOf(3000), TypeCredit.POINT_OF_SALE, BigDecimal.ZERO));
        Insurance insurance = insuranceRepository.save(new Insurance("Безработица", BigDecimal.valueOf(100), BigDecimal.valueOf(1.4)));
        Credit credit = creditRepository.save(new Credit(productGroup, BigDecimal.valueOf(100000), 12, BigDecimal.valueOf(10), insurance, true, false, false, false, userCurrent));

        PreliminaryCreditResult preliminaryCreditResult = preliminaryCreditResultService.calculatePreliminaryCreditResult(credit);

        Assert.assertEquals(preliminaryCreditResult.getInsuranceBonus().stripTrailingZeros(), BigDecimal.valueOf(1400).stripTrailingZeros());
        Assert.assertEquals(preliminaryCreditResult.getInsuranceVolume().stripTrailingZeros(), BigDecimal.valueOf(100000).stripTrailingZeros());
        Assert.assertEquals(preliminaryCreditResult.getCreditPreviously().stripTrailingZeros(), BigDecimal.valueOf(156).stripTrailingZeros());
        Assert.assertEquals(preliminaryCreditResult.getCreditTotal().stripTrailingZeros(), BigDecimal.valueOf(156).stripTrailingZeros());
        Assert.assertEquals(preliminaryCreditResult.getPremium().stripTrailingZeros(), BigDecimal.valueOf(1556).stripTrailingZeros());
    }

    @Test
    public void calculatePreliminaryCreditResultProductTypeCASH_ON_CARD() {
        User userCurrent = userRepository.save(new User("userOne", "pass", "emailOne@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        ProductGroup productGroup = productGroupRepository.save(new ProductGroup("Наличные на карту тип 4", BigDecimal.valueOf(0.8), BigDecimal.ZERO, BigDecimal.valueOf(6000), TypeCredit.CASH_ON_CARD, BigDecimal.valueOf(100000)));
        Insurance insurance = insuranceRepository.save(new Insurance("Безработица", BigDecimal.valueOf(100), BigDecimal.valueOf(1.4)));
        Credit credit = creditRepository.save(new Credit(productGroup, BigDecimal.valueOf(100001), 12, BigDecimal.valueOf(10), insurance, true, false, false, false, userCurrent));

        PreliminaryCreditResult preliminaryCreditResult = preliminaryCreditResultService.calculatePreliminaryCreditResult(credit);

        Assert.assertEquals(preliminaryCreditResult.getInsuranceBonus().stripTrailingZeros(), BigDecimal.valueOf(1400.014).stripTrailingZeros());
        Assert.assertEquals(preliminaryCreditResult.getInsuranceVolume().stripTrailingZeros(), BigDecimal.valueOf(100001).stripTrailingZeros());
        Assert.assertEquals(preliminaryCreditResult.getCreditPreviously().stripTrailingZeros(), BigDecimal.valueOf(800.008).stripTrailingZeros());
        Assert.assertEquals(preliminaryCreditResult.getCreditTotal().stripTrailingZeros(), BigDecimal.valueOf(800.008).stripTrailingZeros());
        Assert.assertEquals(preliminaryCreditResult.getPremium().stripTrailingZeros(), BigDecimal.valueOf(2200.022).stripTrailingZeros());
    }

    @Test
    public void calculatePreliminaryCreditResultProductTypePOINT_OF_SALESelfRejectTrueInsuranceIsNot() {
        User userCurrent = userRepository.save(new User("userOne", "pass", "emailOne@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        ProductGroup productGroup = productGroupRepository.save(new ProductGroup("Услуги", BigDecimal.valueOf(0.13), BigDecimal.valueOf(30), BigDecimal.valueOf(3000), TypeCredit.POINT_OF_SALE, BigDecimal.ZERO));
        Insurance insuranceNot = insuranceRepository.save(new Insurance("Нет", BigDecimal.ZERO, BigDecimal.ZERO));
        Credit credit = creditRepository.save(new Credit(productGroup, BigDecimal.valueOf(600000), 24, BigDecimal.valueOf(10), insuranceNot, true, true, true, true, userCurrent));

        PreliminaryCreditResult preliminaryCreditResult = preliminaryCreditResultService.calculatePreliminaryCreditResult(credit);

        Assert.assertEquals(preliminaryCreditResult.getInsuranceBonus().stripTrailingZeros(), BigDecimal.ZERO);
        Assert.assertEquals(preliminaryCreditResult.getInsuranceVolume().stripTrailingZeros(), BigDecimal.ZERO);
        Assert.assertEquals(preliminaryCreditResult.getCreditPreviously().stripTrailingZeros(), BigDecimal.valueOf(1872).stripTrailingZeros());
        Assert.assertEquals(preliminaryCreditResult.getCreditTotal().stripTrailingZeros(), BigDecimal.valueOf(886).stripTrailingZeros());
        Assert.assertEquals(preliminaryCreditResult.getPremium().stripTrailingZeros(), BigDecimal.valueOf(886).stripTrailingZeros());
    }
}
