package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.sumarokov.premium_calculation.config.AbstractApplicationTest;
import ru.sumarokov.premium_calculation.entity.*;
import ru.sumarokov.premium_calculation.helper.Role;
import ru.sumarokov.premium_calculation.helper.TypeCredit;
import ru.sumarokov.premium_calculation.repository.*;

import java.math.BigDecimal;


public class InsuranceResultServiceTest extends AbstractApplicationTest {

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
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeMethod
    void setUp() {
        //jdbcTemplate.update("truncate preliminary_credit_result cascade;");
        preliminaryCreditResultRepository.deleteAll();
        insuranceResultRepository.deleteAll();
        creditRepository.deleteAll();
        userRepository.deleteAll();
        productGroupRepository.deleteAll();
        insuranceRepository.deleteAll();
    }

    @Test
    public void getInsuranceResultUserTest() {
        User userOne = userRepository.save(new User("userOne", "pass", "emailOne@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        User userTwo = userRepository.save(new User("userTwo", "pass", "emailTwo@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        User userThree = userRepository.save(new User("userThree", "pass", "emailThree@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        InsuranceResult resultExpected = insuranceResultRepository.save(new InsuranceResult(BigDecimal.valueOf(1000), BigDecimal.valueOf(75), BigDecimal.valueOf(10000), userOne));
        User userExpected = resultExpected.getUser();
        insuranceResultRepository.save(new InsuranceResult(BigDecimal.valueOf(2000), BigDecimal.valueOf(95), BigDecimal.valueOf(20000), userTwo));
        insuranceResultRepository.save(new InsuranceResult(BigDecimal.valueOf(3000), BigDecimal.valueOf(95), BigDecimal.valueOf(30000), userThree));

        InsuranceResult insuranceResults = insuranceResultService.getInsuranceResult(userExpected);

        Assert.assertEquals(insuranceResults.getTotalBonus(), resultExpected.getTotalBonus());
        Assert.assertEquals(insuranceResults.getSumInsuranceVolume(), resultExpected.getSumInsuranceVolume());
        Assert.assertEquals(insuranceResults.getPenetration(), resultExpected.getPenetration());
        Assert.assertEquals(insuranceResults.getId(), resultExpected.getId());
        Assert.assertEquals(insuranceResults.getUser().getUsername(), resultExpected.getUser().getUsername());
    }

    @Test
    public void getInsuranceResultNewUserTest() {
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
    public void calculateInsuranceResult() {
        User userCurrent = userRepository.save(new User("userOne", "pass", "emailOne@mail.ru", Role.ROLE_CREDIT_SPECIALIST));
        ProductGroup productGroup = productGroupRepository.save(new ProductGroup("group", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, TypeCredit.CASH_ON_CARD, BigDecimal.ZERO));
        Insurance insurance = insuranceRepository.save(new Insurance("insurance", BigDecimal.ZERO, BigDecimal.ZERO));
        Credit credit1 = creditRepository.save(new Credit(productGroup, BigDecimal.ZERO, 0, BigDecimal.ZERO, insurance, false, false, false, false, userCurrent));
        Credit credit2 = creditRepository.save(new Credit(productGroup, BigDecimal.ZERO, 0, BigDecimal.ZERO, insurance, false, false, false, false, userCurrent));
        Credit credit3 = creditRepository.save(new Credit(productGroup, BigDecimal.ZERO, 0, BigDecimal.ZERO, insurance, false, false, false, false, userCurrent));
        PreliminaryCreditResult result1 = preliminaryCreditResultRepository.save(new PreliminaryCreditResult(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, credit1));
        PreliminaryCreditResult result2 = preliminaryCreditResultRepository.save(new PreliminaryCreditResult(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, credit2));
        PreliminaryCreditResult result3 = preliminaryCreditResultRepository.save(new PreliminaryCreditResult(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, credit3));

        InsuranceResult insuranceResult = insuranceResultService.calculateInsuranceResult(userCurrent);

        Assert.assertEquals(insuranceResult.getTotalBonus(), BigDecimal.ZERO);
        Assert.assertEquals(insuranceResult.getSumInsuranceVolume(), BigDecimal.ZERO);
        Assert.assertEquals(insuranceResult.getPenetration(), BigDecimal.ZERO);
        Assert.assertEquals(insuranceResult.getUser().getUsername(), userCurrent.getUsername());
    }

    @Test
    public void calculateInsuranceResultWithoutCredits() {
        User userCurrent = userRepository.save(new User("userOne", "pass", "emailOne@mail.ru", Role.ROLE_CREDIT_SPECIALIST));

        InsuranceResult insuranceResult = insuranceResultService.calculateInsuranceResult(userCurrent);

        Assert.assertEquals(insuranceResult.getUser().getUsername(), "userOne");
        Assert.assertEquals(insuranceResult.getSumInsuranceVolume(), BigDecimal.ZERO);
        Assert.assertEquals(insuranceResult.getPenetration(), BigDecimal.ZERO);
        Assert.assertEquals(insuranceResult.getTotalBonus(), BigDecimal.ZERO);
    }
}
