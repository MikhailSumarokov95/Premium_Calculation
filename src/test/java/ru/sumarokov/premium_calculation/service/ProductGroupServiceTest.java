package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.sumarokov.premium_calculation.config.AbstractApplicationTestWithDeleteAllTableBeforeMethod;
import ru.sumarokov.premium_calculation.entity.ProductGroup;
import ru.sumarokov.premium_calculation.exception.EntityNotFoundException;
import ru.sumarokov.premium_calculation.helper.TypeCredit;
import ru.sumarokov.premium_calculation.repository.ProductGroupRepository;

import java.math.BigDecimal;

public class ProductGroupServiceTest extends AbstractApplicationTestWithDeleteAllTableBeforeMethod {

    @Autowired
    private ProductGroupRepository productGroupRepository;

    @Autowired
    private ProductGroupService productGroupService;

    @Test
    public void getProductGroup() {
        productGroupRepository.save(new ProductGroup("Услуги", BigDecimal.valueOf(0.13), BigDecimal.valueOf(30), BigDecimal.valueOf(3000), TypeCredit.POINT_OF_SALE, BigDecimal.ZERO));
        ProductGroup productGroup2 = productGroupRepository.save(new ProductGroup("Наличные на карту тип 4", BigDecimal.valueOf(0.6), BigDecimal.valueOf(0), BigDecimal.valueOf(6000), TypeCredit.CASH_ON_CARD, BigDecimal.valueOf(100000)));

        ProductGroup productGroup = productGroupService.getProductGroup(productGroup2.getId());

        Assert.assertEquals(productGroup.getName(), "Наличные на карту тип 4");
        Assert.assertEquals(productGroup.getFactorPremium().stripTrailingZeros(), BigDecimal.valueOf(0.6).stripTrailingZeros());
        Assert.assertEquals(productGroup.getMinPremium().stripTrailingZeros(), BigDecimal.ZERO);
        Assert.assertEquals(productGroup.getMaxPremium().stripTrailingZeros(), BigDecimal.valueOf(6000).stripTrailingZeros());
        Assert.assertEquals(productGroup.getTypeCredit(), TypeCredit.CASH_ON_CARD);
        Assert.assertEquals(productGroup.getMinAmountForCalculatingCreditPremium().stripTrailingZeros(), BigDecimal.valueOf(100000).stripTrailingZeros());
        Assert.assertEquals(productGroup.getId(), productGroup2.getId());
    }

    @Test
    public void getProductGroupEntityNotFound() {
        Assert.assertThrows(EntityNotFoundException.class, () -> productGroupService.getProductGroup(1L));
    }

    @Test
    public void saveProductGroup() {
        productGroupService.saveProductGroup(new ProductGroup("Наличные на карту тип 4", BigDecimal.valueOf(0.6), BigDecimal.valueOf(0), BigDecimal.valueOf(6000), TypeCredit.CASH_ON_CARD, BigDecimal.valueOf(100000)));

        ProductGroup productGroup = productGroupRepository.findAll().get(0);

        Assert.assertEquals(productGroup.getName(), "Наличные на карту тип 4");
        Assert.assertEquals(productGroup.getFactorPremium().stripTrailingZeros(), BigDecimal.valueOf(0.6).stripTrailingZeros());
        Assert.assertEquals(productGroup.getMinPremium().stripTrailingZeros(), BigDecimal.ZERO);
        Assert.assertEquals(productGroup.getMaxPremium().stripTrailingZeros(), BigDecimal.valueOf(6000).stripTrailingZeros());
        Assert.assertEquals(productGroup.getTypeCredit(), TypeCredit.CASH_ON_CARD);
        Assert.assertEquals(productGroup.getMinAmountForCalculatingCreditPremium().stripTrailingZeros(), BigDecimal.valueOf(100000).stripTrailingZeros());
    }

    @Test
    public void deleteProductGroup() {
        productGroupRepository.save(new ProductGroup("Услуги", BigDecimal.valueOf(0.13), BigDecimal.valueOf(30), BigDecimal.valueOf(3000), TypeCredit.POINT_OF_SALE, BigDecimal.ZERO));
        ProductGroup productGroup2 = productGroupRepository.save(new ProductGroup("Наличные на карту тип 4", BigDecimal.valueOf(0.6), BigDecimal.valueOf(0), BigDecimal.valueOf(6000), TypeCredit.CASH_ON_CARD, BigDecimal.valueOf(100000)));

        productGroupService.deleteProductGroup(productGroup2.getId());

        Assert.assertFalse(productGroupRepository.existsById(productGroup2.getId()));
    }

    @Test
    public void deleteProductGroupEntityNotFound() {
        Assert.assertThrows(EntityNotFoundException.class, () -> productGroupService.deleteProductGroup(1L));
    }
}
