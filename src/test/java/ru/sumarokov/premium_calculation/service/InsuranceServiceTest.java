package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.sumarokov.premium_calculation.config.AbstractApplicationTest;
import ru.sumarokov.premium_calculation.entity.Insurance;
import ru.sumarokov.premium_calculation.exception.EntityNotFoundException;
import ru.sumarokov.premium_calculation.repository.InsuranceRepository;

import java.math.BigDecimal;

public class InsuranceServiceTest extends AbstractApplicationTest {

    @Autowired
    private InsuranceService insuranceService;
    @Autowired
    private InsuranceRepository insuranceRepository;

    @Test
    public void getInsurance() {
        insuranceRepository.save(new Insurance("Жизнь", BigDecimal.valueOf(100), BigDecimal.valueOf(1.6)));
        Insurance insurance2 = insuranceRepository.save(new Insurance("Безработица", BigDecimal.valueOf(100), BigDecimal.valueOf(1.4)));

        Insurance insurance = insuranceService.getInsurance(insurance2.getId());

        Assert.assertEquals(insurance.getName(), "Безработица");
        Assert.assertEquals(insurance.getFactorInsuranceVolume().stripTrailingZeros(), BigDecimal.valueOf(100).stripTrailingZeros());
        Assert.assertEquals(insurance.getFactorInsuranceBonus().stripTrailingZeros(), BigDecimal.valueOf(1.4).stripTrailingZeros());
        Assert.assertEquals(insurance.getId(), insurance2.getId());
    }

    @Test
    public void getInsuranceEntityNotFound() {
        Assert.assertThrows(EntityNotFoundException.class, () -> insuranceService.getInsurance(1L));
    }

    @Test
    public void saveInsurance() {
        insuranceRepository.save(new Insurance("Безработица", BigDecimal.valueOf(100), BigDecimal.valueOf(1.4)));

        Insurance insurance = insuranceRepository.findAll().get(0);

        Assert.assertEquals(insurance.getName(), "Безработица");
        Assert.assertEquals(insurance.getFactorInsuranceVolume().stripTrailingZeros(), BigDecimal.valueOf(100).stripTrailingZeros());
        Assert.assertEquals(insurance.getFactorInsuranceBonus().stripTrailingZeros(), BigDecimal.valueOf(1.4).stripTrailingZeros());
    }

    @Test
    public void deleteInsurance() {
        insuranceRepository.save(new Insurance("Жизнь", BigDecimal.valueOf(100), BigDecimal.valueOf(1.6)));
        Insurance insurance2 = insuranceRepository.save(new Insurance("Безработица", BigDecimal.valueOf(100), BigDecimal.valueOf(1.4)));

        insuranceService.deleteInsurance(insurance2.getId());

        Assert.assertFalse(insuranceRepository.existsById(insurance2.getId()));
    }

    @Test
    public void deleteInsuranceEntityNotFound() {
        Assert.assertThrows(EntityNotFoundException.class, () -> insuranceService.deleteInsurance(1L));
    }
}
