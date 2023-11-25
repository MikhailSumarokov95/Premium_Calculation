package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.sumarokov.premium_calculation.config.AbstractApplicationTest;
import ru.sumarokov.premium_calculation.entity.PremiumLimit;
import ru.sumarokov.premium_calculation.exception.EntityNotFoundException;
import ru.sumarokov.premium_calculation.repository.PremiumLimitRepository;

import java.math.BigDecimal;

public class PremiumLimitServiceTest extends AbstractApplicationTest {

    @Autowired
    private PremiumLimitService premiumLimitService;
    @Autowired
    private PremiumLimitRepository premiumLimitRepository;

    @Test
    public void getPremiumLimit() {
        PremiumLimit premiumLimitActual = premiumLimitRepository.save(new PremiumLimit(BigDecimal.valueOf(100000), true));
        premiumLimitRepository.save(new PremiumLimit(BigDecimal.valueOf(200000), false));

        PremiumLimit premiumLimit = premiumLimitService.getPremiumLimit();

        Assert.assertEquals(premiumLimitActual.getMaxTotalPremium(), BigDecimal.valueOf(100000));
        Assert.assertTrue(premiumLimit.getIsActual());
        Assert.assertEquals(premiumLimit.getId(), premiumLimitActual.getId());
    }

    @Test
    public void getPremiumLimitEntityNotFound() {
        Assert.assertThrows(EntityNotFoundException.class, () -> premiumLimitService.getPremiumLimit());
    }

    @Test
    public void savePremiumLimit() {
        premiumLimitService.savePremiumLimit(new PremiumLimit(BigDecimal.valueOf(100000), true));

        PremiumLimit premiumLimit = premiumLimitRepository.findByIsActualTrue().orElseThrow();

        Assert.assertEquals(premiumLimit.getMaxTotalPremium(), BigDecimal.valueOf(100000));
        Assert.assertTrue(premiumLimit.getIsActual());
    }
}
