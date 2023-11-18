package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.Credit;
import ru.sumarokov.premium_calculation.entity.ProductivityLevel;
import ru.sumarokov.premium_calculation.entity.ProductivityResult;
import ru.sumarokov.premium_calculation.entity.User;
import ru.sumarokov.premium_calculation.repository.CreditRepository;
import ru.sumarokov.premium_calculation.repository.ProductivityLevelRepository;
import ru.sumarokov.premium_calculation.repository.ProductivityResultRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ProductivityResultService {

    private final ProductivityLevelRepository productivityLevelRepository;
    private final ProductivityResultRepository productivityResultRepository;
    private final CreditRepository creditRepository;
    private final InsuranceResultService insuranceResultService;
    private final AuthService authService;

    @Autowired
    public ProductivityResultService(ProductivityLevelRepository productivityLevelRepository,
                                     ProductivityResultRepository productivityResultRepository,
                                     CreditRepository creditRepository,
                                     InsuranceResultService insuranceResultService,
                                     AuthService authService) {
        this.productivityLevelRepository = productivityLevelRepository;
        this.productivityResultRepository = productivityResultRepository;
        this.creditRepository = creditRepository;
        this.insuranceResultService = insuranceResultService;
        this.authService = authService;
    }

    public ProductivityResult getProductivityResult() {
        User user = authService.getUser();
        return productivityResultRepository.findByUserId(user.getId())
                .orElse(new ProductivityResult(user));
    }

    public ProductivityResult calculateProductivityResult() {
        ProductivityResult productivityResult = getProductivityResult();

        List<Credit> credits = creditRepository.findByUserId(authService.getUser().getId());
        Long countCredits = (long) credits.size();
        BigDecimal sumAmountCredits = calculateSumAmountCredits(credits);
        BigDecimal insurancePenetration = insuranceResultService.calculateInsuranceResult().getPenetration();
        BigDecimal smsPenetration = calculateSmsPenetration(credits, BigDecimal.valueOf(countCredits));
        List<ProductivityLevel> productivityLevels = productivityLevelRepository.findAllByOrderByPremiumDesc();

        productivityResult.setCountCreditsLevel(calculateCountCreditsLevel(countCredits, productivityLevels));
        productivityResult.setSumAmountCreditsLevel(calculateSumAmountCreditsLevel(sumAmountCredits, productivityLevels));
        productivityResult.setSmsPenetrationLevel(calculateSmsPenetrationLevel(smsPenetration, productivityLevels));
        productivityResult.setInsurancePenetrationLevel(calculateInsurancePenetrationLevel(insurancePenetration, productivityLevels));
        productivityResult.setGeneralLevel(calculateGeneralLevel(countCredits, sumAmountCredits, insurancePenetration, smsPenetration, productivityLevels));
        return productivityResultRepository.save(productivityResult);
    }

    private BigDecimal calculateSumAmountCredits(List<Credit> credits) {
        return credits.stream()
                .map(Credit::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateSmsPenetration(List<Credit> credits, BigDecimal countCredits) {
        BigDecimal countCreditsWithSms = BigDecimal.valueOf(credits.stream()
                .filter(Credit::getIsConnectedSms)
                .count());
        if (countCredits.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        } else {
            return countCreditsWithSms
                    .divide(countCredits, 5, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }
    }

    private ProductivityLevel calculateCountCreditsLevel(Long countCredits,
                                                         List<ProductivityLevel> productivityLevels) {
        return productivityLevels.stream()
                .filter(p -> p.getMinCountCredits() <= countCredits)
                .findFirst()
                .orElseThrow();
    }

    private ProductivityLevel calculateSumAmountCreditsLevel(BigDecimal sumAmountCredits,
                                                             List<ProductivityLevel> productivityLevels) {
        return productivityLevels.stream()
                .filter(p -> p.getMinSumAmountCredits().compareTo(sumAmountCredits) <= 0)
                .findFirst()
                .orElseThrow();
    }

    private ProductivityLevel calculateSmsPenetrationLevel(BigDecimal smsPenetration,
                                                           List<ProductivityLevel> productivityLevels) {
        return productivityLevels.stream()
                .filter(p -> p.getMinPenetrationSms().compareTo(smsPenetration) <= 0)
                .findFirst()
                .orElseThrow();
    }

    private ProductivityLevel calculateInsurancePenetrationLevel(BigDecimal insurancePenetration,
                                                                 List<ProductivityLevel> productivityLevels) {
        return productivityLevels.stream()
                .filter(p -> p.getMinPenetrationInsurance().compareTo(insurancePenetration) <= 0)
                .findFirst()
                .orElseThrow();
    }

    private ProductivityLevel calculateGeneralLevel(Long countCredits,
                                                    BigDecimal sumAmountCredits,
                                                    BigDecimal insurancePenetration,
                                                    BigDecimal smsPenetration,
                                                    List<ProductivityLevel> productivityLevels) {
        return productivityLevels.stream()
                .filter(p -> (p.getMinCountCredits() <= (countCredits)
                        || p.getMinSumAmountCredits().compareTo(sumAmountCredits) <= 0)
                        && p.getMinPenetrationInsurance().compareTo(insurancePenetration) <= 0
                        && p.getMinPenetrationSms().compareTo(smsPenetration) <= 0)
                .findFirst()
                .orElseThrow();
    }
}
