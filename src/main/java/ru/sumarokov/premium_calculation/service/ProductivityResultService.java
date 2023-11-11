package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.ProductivityLevel;
import ru.sumarokov.premium_calculation.entity.ProductivityResult;
import ru.sumarokov.premium_calculation.repository.CreditRepository;
import ru.sumarokov.premium_calculation.repository.ProductivityLevelRepository;
import ru.sumarokov.premium_calculation.repository.ProductivityResultRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ProductivityResultService {

    private final ProductivityLevelRepository productivityLevelRepository;
    private final ProductivityResultRepository productivityResultRepository;
    private final CreditRepository creditRepository;
    private final InsuranceResultService insuranceResultService;

    @Autowired
    public ProductivityResultService(ProductivityLevelRepository productivityLevelRepository,
                                     ProductivityResultRepository productivityResultRepository,
                                     CreditRepository creditRepository,
                                     InsuranceResultService insuranceResultService) {
        this.productivityLevelRepository = productivityLevelRepository;
        this.productivityResultRepository = productivityResultRepository;
        this.creditRepository = creditRepository;
        this.insuranceResultService = insuranceResultService;
    }

    public ProductivityResult getProductivityResult() {
        return productivityResultRepository.findById(1L).orElse(new ProductivityResult());
    }

    public ProductivityResult calculateProductivityResult() {
        ProductivityResult productivityResult = productivityResultRepository
                .findById(1L)
                .orElse(new ProductivityResult());

        productivityResult.setCountCreditsLevel(calculateCountCreditsLevel());
        productivityResult.setSumAmountCreditsLevel(calculateSumAmountCreditsLevel());
        productivityResult.setSmsPenetrationLevel(calculateSmsPenetrationLevel());
        productivityResult.setInsurancePenetrationLevel(calculateInsurancePenetrationLevel());
        productivityResult.setGeneralLevel(calculateGeneralLevel());
        return productivityResultRepository.save(productivityResult);
    }

    private ProductivityLevel calculateCountCreditsLevel() {
        Integer countCredits = creditRepository.getCountCredits();
        return productivityLevelRepository.getCountCreditsLevel(countCredits).orElseThrow();
    }

    private ProductivityLevel calculateSumAmountCreditsLevel() {
        BigDecimal sumAmountCredits = creditRepository.getSumAmountCredits();
        return productivityLevelRepository.getSumAmountCreditsLevel(sumAmountCredits).orElseThrow();
    }

    private ProductivityLevel calculateInsurancePenetrationLevel() {
        BigDecimal insurancePenetration = insuranceResultService.calculateInsuranceResult().getPenetration();
        return productivityLevelRepository.getInsurancePenetrationLevel(insurancePenetration).orElseThrow();
    }

    private ProductivityLevel calculateSmsPenetrationLevel() {
        BigDecimal smsPenetration = calculateSmsPenetration();
        return productivityLevelRepository.getSmsPenetrationLevel(smsPenetration).orElseThrow();
    }

    private BigDecimal calculateSmsPenetration() {
        BigDecimal countCreditsWithSms = BigDecimal.valueOf(creditRepository.getCountCreditsWithSms());
        BigDecimal countCredits = BigDecimal.valueOf(creditRepository.getCountCredits());
        if (countCredits.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        } else {
            return countCreditsWithSms
                    .divide(countCredits, 5, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }
    }

    private ProductivityLevel calculateGeneralLevel() {
        Integer countCredits = creditRepository.getCountCredits();
        BigDecimal sumAmountCredits = creditRepository.getSumAmountCredits();
        BigDecimal insurancePenetration = insuranceResultService.calculateInsuranceResult().getPenetration();
        BigDecimal smsPenetration = calculateSmsPenetration();
        return productivityLevelRepository
                .getGeneralLevel(countCredits, sumAmountCredits, smsPenetration, insurancePenetration)
                .orElseThrow();
    }
}
