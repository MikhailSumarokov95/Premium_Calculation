package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.sumarokov.premium_calculation.config.AbstractApplicationTestWithDeleteAllTableBeforeMethod;
import ru.sumarokov.premium_calculation.entity.*;
import ru.sumarokov.premium_calculation.helper.Role;
import ru.sumarokov.premium_calculation.helper.TypeCredit;
import ru.sumarokov.premium_calculation.repository.*;

import java.math.BigDecimal;


public class InsuranceResultServiceTest extends AbstractApplicationTestWithDeleteAllTableBeforeMethod {

    @Autowired
    private InsuranceResultRepository insuranceResultRepository;
    @Autowired
    private InsuranceResultService insuranceResultService;
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

    @Test
    public void getInsuranceResultUser() {
        User userOne = userRepository.save(new User("userOne", "pass", "emailOne@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        User userTwo = userRepository.save(new User("userTwo", "pass", "emailTwo@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        User userThree = userRepository.save(new User("userThree", "pass", "emailThree@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        InsuranceResult resultExpected = insuranceResultRepository.save(new InsuranceResult(BigDecimal.valueOf(1000), BigDecimal.valueOf(75), BigDecimal.valueOf(10000), userOne));
        User userExpected = resultExpected.getUser();
        insuranceResultRepository.save(new InsuranceResult(BigDecimal.valueOf(2000), BigDecimal.valueOf(95), BigDecimal.valueOf(20000), userTwo));
        insuranceResultRepository.save(new InsuranceResult(BigDecimal.valueOf(3000), BigDecimal.valueOf(95), BigDecimal.valueOf(30000), userThree));

        InsuranceResult insuranceResults = insuranceResultService.getInsuranceResult(userExpected);

        Assert.assertEquals(insuranceResults.getTotalBonus().stripTrailingZeros(), resultExpected.getTotalBonus().stripTrailingZeros());
        Assert.assertEquals(insuranceResults.getSumInsuranceVolume().stripTrailingZeros(), resultExpected.getSumInsuranceVolume().stripTrailingZeros());
        Assert.assertEquals(insuranceResults.getPenetration().stripTrailingZeros(), resultExpected.getPenetration().stripTrailingZeros());
        Assert.assertEquals(insuranceResults.getId(), resultExpected.getId());
        Assert.assertEquals(insuranceResults.getUser().getUsername(), resultExpected.getUser().getUsername());
    }

    @Test
    public void getInsuranceResultNewUser() {
        User userCurrent = userRepository.save(new User("userOne", "pass", "emailOne@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        User userTwo = userRepository.save(new User("userTwo", "pass", "emailTwo@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        User userThree = userRepository.save(new User("userThree", "pass", "emailThree@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        insuranceResultRepository.save(new InsuranceResult(BigDecimal.valueOf(2000), BigDecimal.valueOf(95), BigDecimal.valueOf(20000), userTwo));
        insuranceResultRepository.save(new InsuranceResult(BigDecimal.valueOf(3000), BigDecimal.valueOf(95), BigDecimal.valueOf(30000), userThree));

        InsuranceResult insuranceResults = insuranceResultService.getInsuranceResult(userCurrent);

        Assert.assertNull(insuranceResults.getTotalBonus());
        Assert.assertNull(insuranceResults.getSumInsuranceVolume());
        Assert.assertNull(insuranceResults.getPenetration());
        Assert.assertNull(insuranceResults.getId());
        Assert.assertEquals(insuranceResults.getUser().getUsername(), userCurrent.getUsername());
    }

    @Test
    public void getInsuranceResultNonExistent() {
        User userOne = userRepository.save(new User("userOne", "pass", "emailOne@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        User userTwo = userRepository.save(new User("userTwo", "pass", "emailTwo@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        User userThree = userRepository.save(new User("userThree", "pass", "emailThree@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        InsuranceResult resultExpected = new InsuranceResult(BigDecimal.valueOf(1000), BigDecimal.valueOf(75), BigDecimal.valueOf(10000), userOne);
        User userCurrent = resultExpected.getUser();
        insuranceResultRepository.save(new InsuranceResult(BigDecimal.valueOf(2000), BigDecimal.valueOf(95), BigDecimal.valueOf(20000), userTwo));
        insuranceResultRepository.save(new InsuranceResult(BigDecimal.valueOf(3000), BigDecimal.valueOf(95), BigDecimal.valueOf(30000), userThree));

        InsuranceResult insuranceResults = insuranceResultService.getInsuranceResult(userCurrent);

        Assert.assertNull(insuranceResults.getTotalBonus());
        Assert.assertNull(insuranceResults.getSumInsuranceVolume());
        Assert.assertNull(insuranceResults.getPenetration());
        Assert.assertNull(insuranceResults.getId());
        Assert.assertEquals(insuranceResults.getUser().getUsername(), userCurrent.getUsername());
    }

    @Test
    public void calculateInsuranceResultAllZero() {
        User userCurrent = userRepository.save(new User("userOne", "pass", "emailOne@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        ProductGroup productGroup = productGroupRepository.save(new ProductGroup("group", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, TypeCredit.CASH_ON_CARD, BigDecimal.ZERO));
        Insurance insurance = insuranceRepository.save(new Insurance("insurance", BigDecimal.ZERO, BigDecimal.ZERO));
        Credit credit1 = creditRepository.save(new Credit(productGroup, BigDecimal.ZERO, 0, BigDecimal.ZERO, insurance, false, false, false, false, userCurrent));
        Credit credit2 = creditRepository.save(new Credit(productGroup, BigDecimal.ZERO, 0, BigDecimal.ZERO, insurance, false, false, false, false, userCurrent));
        Credit credit3 = creditRepository.save(new Credit(productGroup, BigDecimal.ZERO, 0, BigDecimal.ZERO, insurance, false, false, false, false, userCurrent));
        preliminaryCreditResultRepository.save(new PreliminaryCreditResult(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, credit1));
        preliminaryCreditResultRepository.save(new PreliminaryCreditResult(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, credit2));
        preliminaryCreditResultRepository.save(new PreliminaryCreditResult(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, credit3));

        InsuranceResult insuranceResult = insuranceResultService.calculateInsuranceResult(userCurrent);

        Assert.assertEquals(insuranceResult.getTotalBonus().stripTrailingZeros(), BigDecimal.ZERO);
        Assert.assertEquals(insuranceResult.getSumInsuranceVolume().stripTrailingZeros(), BigDecimal.ZERO);
        Assert.assertEquals(insuranceResult.getPenetration().stripTrailingZeros(), BigDecimal.ZERO);
        Assert.assertEquals(insuranceResult.getUser().getUsername(), userCurrent.getUsername());
    }

    @Test
    public void calculateInsuranceResultWithoutCredits() {
        User userCurrent = userRepository.save(new User("userOne", "pass", "emailOne@mail.ru", Role.ROLE_CREDIT_SPECIALIST));

        InsuranceResult insuranceResult = insuranceResultService.calculateInsuranceResult(userCurrent);

        Assert.assertEquals(insuranceResult.getUser().getUsername(), "userOne");
        Assert.assertEquals(insuranceResult.getSumInsuranceVolume().stripTrailingZeros(), BigDecimal.ZERO);
        Assert.assertEquals(insuranceResult.getPenetration().stripTrailingZeros(), BigDecimal.ZERO);
        Assert.assertEquals(insuranceResult.getTotalBonus().stripTrailingZeros(), BigDecimal.ZERO);
    }

    @Test
    public void calculateInsuranceResult() {
        User userCurrent = userRepository.save(new User("userOne", "pass", "emailOne@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        ProductGroup productGroup = productGroupRepository.save(new ProductGroup("Услуги", BigDecimal.valueOf(0.13), BigDecimal.valueOf(30), BigDecimal.valueOf(3000), TypeCredit.POINT_OF_SALE, BigDecimal.ZERO));
        Insurance insurance = insuranceRepository.save(new Insurance("Безработица", BigDecimal.valueOf(100), BigDecimal.valueOf(1.4)));
        Insurance insuranceNot = insuranceRepository.save(new Insurance("Нет", BigDecimal.ZERO, BigDecimal.ZERO));
        Credit credit1 = creditRepository.save(new Credit(productGroup, BigDecimal.valueOf(100000), 12, BigDecimal.valueOf(10), insurance, true, false, false, false, userCurrent));
        Credit credit2 = creditRepository.save(new Credit(productGroup, BigDecimal.valueOf(200000), 12, BigDecimal.valueOf(10), insurance, true, false, false, false, userCurrent));
        Credit credit3 = creditRepository.save(new Credit(productGroup, BigDecimal.valueOf(300000), 24, BigDecimal.valueOf(10), insuranceNot, true, false, false, false, userCurrent));
        preliminaryCreditResultRepository.save(new PreliminaryCreditResult(BigDecimal.valueOf(1556), BigDecimal.valueOf(156), BigDecimal.valueOf(1400), BigDecimal.valueOf(100000), BigDecimal.valueOf(156), credit1));
        preliminaryCreditResultRepository.save(new PreliminaryCreditResult(BigDecimal.valueOf(3112), BigDecimal.valueOf(312), BigDecimal.valueOf(2800), BigDecimal.valueOf(200000), BigDecimal.valueOf(312), credit2));
        preliminaryCreditResultRepository.save(new PreliminaryCreditResult(BigDecimal.valueOf(936), BigDecimal.valueOf(936), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.valueOf(936), credit3));

        InsuranceResult insuranceResult = insuranceResultService.calculateInsuranceResult(userCurrent);

        Assert.assertEquals(insuranceResult.getTotalBonus().stripTrailingZeros(), BigDecimal.valueOf(4200).stripTrailingZeros());
        Assert.assertEquals(insuranceResult.getSumInsuranceVolume().stripTrailingZeros(), BigDecimal.valueOf(300000).stripTrailingZeros());
        Assert.assertEquals(insuranceResult.getPenetration().stripTrailingZeros(), BigDecimal.valueOf(50).stripTrailingZeros());
        Assert.assertEquals(insuranceResult.getUser().getUsername(), userCurrent.getUsername());
    }
}
