package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.sumarokov.premium_calculation.config.AbstractApplicationTest;
import ru.sumarokov.premium_calculation.entity.ProductivityLevel;
import ru.sumarokov.premium_calculation.exception.EntityNotFoundException;
import ru.sumarokov.premium_calculation.repository.ProductivityLevelRepository;
import org.springframework.security.access.AccessDeniedException;

import java.math.BigDecimal;

public class ProductivityLevelServiceTest extends AbstractApplicationTest {

    @Autowired
    private ProductivityLevelService productivityLevelService;
    @Autowired
    private ProductivityLevelRepository productivityLevelRepository;

    @Test
    public void getProductivityLevel() {
        productivityLevelRepository.save(new ProductivityLevel("Уровень 0", BigDecimal.ZERO, 0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, true));
        ProductivityLevel productivityLevel2 = productivityLevelRepository.save(new ProductivityLevel("Медный", BigDecimal.valueOf(7000), 16, BigDecimal.valueOf(500000), BigDecimal.valueOf(75), BigDecimal.ZERO, false));

        ProductivityLevel productivityLevel = productivityLevelService.getProductivityLevel(productivityLevel2.getId());

        Assert.assertEquals(productivityLevel.getName(), "Медный");
        Assert.assertEquals(productivityLevel.getPremium().stripTrailingZeros(), BigDecimal.valueOf(7000).stripTrailingZeros());
        Assert.assertEquals(productivityLevel.getMinCountCredits(), 16);
        Assert.assertEquals(productivityLevel.getMinSumAmountCredits().stripTrailingZeros(), BigDecimal.valueOf(500000).stripTrailingZeros());
        Assert.assertEquals(productivityLevel.getMinPenetrationSms().stripTrailingZeros(), BigDecimal.valueOf(75).stripTrailingZeros());
        Assert.assertEquals(productivityLevel.getMinPenetrationInsurance().stripTrailingZeros(), BigDecimal.ZERO);
        Assert.assertFalse(productivityLevel.getIsDefault());
        Assert.assertEquals(productivityLevel.getId(), productivityLevel2.getId());
    }

    @Test
    public void getProductivityLevelNotFound() {
        Assert.assertThrows(EntityNotFoundException.class, () -> productivityLevelService.getProductivityLevel(1L));
    }

    @Test
    public void saveProductivityLevel() {
        productivityLevelRepository.save(new ProductivityLevel("Медный", BigDecimal.valueOf(7000), 16, BigDecimal.valueOf(500000), BigDecimal.valueOf(75), BigDecimal.ZERO, false));

        ProductivityLevel productivityLevel = productivityLevelRepository.findAll().get(0);

        Assert.assertEquals(productivityLevel.getName(), "Медный");
        Assert.assertEquals(productivityLevel.getPremium().stripTrailingZeros(), BigDecimal.valueOf(7000).stripTrailingZeros());
        Assert.assertEquals(productivityLevel.getMinCountCredits(), 16);
        Assert.assertEquals(productivityLevel.getMinSumAmountCredits().stripTrailingZeros(), BigDecimal.valueOf(500000).stripTrailingZeros());
        Assert.assertEquals(productivityLevel.getMinPenetrationSms().stripTrailingZeros(), BigDecimal.valueOf(75).stripTrailingZeros());
        Assert.assertEquals(productivityLevel.getMinPenetrationInsurance().stripTrailingZeros(), BigDecimal.ZERO);
        Assert.assertFalse(productivityLevel.getIsDefault());
    }

    @Test
    public void deleteProductivityLevel() {
        productivityLevelRepository.save(new ProductivityLevel("Уровень 0", BigDecimal.ZERO, 0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, true));
        ProductivityLevel productivityLevel2 = productivityLevelRepository.save(new ProductivityLevel("Медный", BigDecimal.valueOf(7000), 16, BigDecimal.valueOf(500000), BigDecimal.valueOf(75), BigDecimal.ZERO, false));

        productivityLevelService.deleteProductivityLevel(productivityLevel2.getId());

        Assert.assertFalse(productivityLevelRepository.existsById(productivityLevel2.getId()));
    }

    @Test
    public void deleteProductivityLevelIsDefaultTrue() {
        ProductivityLevel productivityLevelDefault = productivityLevelRepository.save(new ProductivityLevel("Уровень 0", BigDecimal.ZERO, 0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, true));
        productivityLevelRepository.save(new ProductivityLevel("Медный", BigDecimal.valueOf(7000), 16, BigDecimal.valueOf(500000), BigDecimal.valueOf(75), BigDecimal.ZERO, false));

        Assert.assertThrows(AccessDeniedException.class, () -> productivityLevelService.deleteProductivityLevel(productivityLevelDefault.getId()));
    }

    @Test
    public void deleteProductivityLevelEntityNotFound() {
        Assert.assertThrows(EntityNotFoundException.class, () -> productivityLevelService.deleteProductivityLevel(1L));
    }
}
