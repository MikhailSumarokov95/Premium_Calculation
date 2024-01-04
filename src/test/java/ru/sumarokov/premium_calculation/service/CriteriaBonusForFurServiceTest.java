package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.sumarokov.premium_calculation.config.AbstractApplicationTestWithDeleteAllTableBeforeMethod;
import ru.sumarokov.premium_calculation.entity.CriteriaBonusForFur;
import ru.sumarokov.premium_calculation.exception.EntityNotFoundException;
import ru.sumarokov.premium_calculation.repository.CriteriaBonusForFurRepository;

import java.math.BigDecimal;

public class CriteriaBonusForFurServiceTest extends AbstractApplicationTestWithDeleteAllTableBeforeMethod {

    @Autowired
    private CriteriaBonusForFurService criteriaBonusForFurService;
    @Autowired
    private CriteriaBonusForFurRepository criteriaBonusForFurRepository;

    @Test
    public void getCriteriaBonusForFur() {
        criteriaBonusForFurRepository.save(new CriteriaBonusForFur(BigDecimal.valueOf(750000), BigDecimal.valueOf(70), BigDecimal.valueOf(7500)));
        CriteriaBonusForFur criteriaBonusForFur2 = criteriaBonusForFurRepository.save(new CriteriaBonusForFur(BigDecimal.valueOf(1000000), BigDecimal.valueOf(75), BigDecimal.valueOf(75000)));

        CriteriaBonusForFur criteriaBonusForFur = criteriaBonusForFurService.getCriteriaBonusForFur(criteriaBonusForFur2.getId());

        Assert.assertEquals(criteriaBonusForFur.getBonus().stripTrailingZeros(), BigDecimal.valueOf(75000).stripTrailingZeros());
        Assert.assertEquals(criteriaBonusForFur.getMinSum().stripTrailingZeros(), BigDecimal.valueOf(1000000).stripTrailingZeros());
        Assert.assertEquals(criteriaBonusForFur.getMinSms().stripTrailingZeros(), BigDecimal.valueOf(75).stripTrailingZeros());
        Assert.assertEquals(criteriaBonusForFur.getId(), criteriaBonusForFur2.getId());
    }

    @Test
    public void getCriteriaBonusForFurEntityNotFound() {
        Assert.assertThrows(EntityNotFoundException.class, () -> criteriaBonusForFurService.getCriteriaBonusForFur(1L));
    }

    @Test
    public void saveCriteriaBonusForFur() {
        criteriaBonusForFurService.saveCriteriaBonusForFur(new CriteriaBonusForFur(BigDecimal.valueOf(1000000), BigDecimal.valueOf(75), BigDecimal.valueOf(75000)));

        CriteriaBonusForFur criteriaBonusForFur = criteriaBonusForFurRepository.findAll().get(0);

        Assert.assertEquals(criteriaBonusForFur.getBonus().stripTrailingZeros(), BigDecimal.valueOf(75000).stripTrailingZeros());
        Assert.assertEquals(criteriaBonusForFur.getMinSum().stripTrailingZeros(), BigDecimal.valueOf(1000000).stripTrailingZeros());
        Assert.assertEquals(criteriaBonusForFur.getMinSms().stripTrailingZeros(), BigDecimal.valueOf(75).stripTrailingZeros());
    }

    @Test
    public void deleteCriteriaBonusForFur() {
        criteriaBonusForFurRepository.save(new CriteriaBonusForFur(BigDecimal.valueOf(750000), BigDecimal.valueOf(70), BigDecimal.valueOf(7500)));
        CriteriaBonusForFur criteriaBonusForFur2 = criteriaBonusForFurRepository.save(new CriteriaBonusForFur(BigDecimal.valueOf(1000000), BigDecimal.valueOf(75), BigDecimal.valueOf(75000)));

        criteriaBonusForFurService.deleteCriteriaBonusForFur(criteriaBonusForFur2.getId());

        Assert.assertFalse(criteriaBonusForFurRepository.existsById(criteriaBonusForFur2.getId()));
    }

    @Test
    public void deleteCriteriaBonusForFurEntityNotFound() {
        Assert.assertThrows(EntityNotFoundException.class, () -> criteriaBonusForFurService.deleteCriteriaBonusForFur(1L));
    }
}
