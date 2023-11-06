package ru.sumarokov.premium_calculation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.sumarokov.premium_calculation.entity.*;
import ru.sumarokov.premium_calculation.helper.TypeCredit;
import ru.sumarokov.premium_calculation.repository.*;

import java.math.BigDecimal;

@SpringBootApplication
public class PremiumCalculationApplication implements CommandLineRunner {

    private InsuranceRepository insuranceRepository;
    private ProductGroupRepository productGroupRepository;
    private CriteriaBonusForFurRepository criteriaBonusForFurRepository;
    private ProductivityLevelRepository productivityLevelRepository;
    private PremiumLimitRepository premiumLimitRepository;

    @Autowired
    public PremiumCalculationApplication(InsuranceRepository insuranceRepository,
                                         ProductGroupRepository productGroupRepository,
                                         CriteriaBonusForFurRepository criteriaBonusForFurRepository,
                                         ProductivityLevelRepository productivityLevelRepository,
                                         PremiumLimitRepository premiumLimitRepository) {
        this.insuranceRepository = insuranceRepository;
        this.productGroupRepository = productGroupRepository;
        this.criteriaBonusForFurRepository = criteriaBonusForFurRepository;
        this.productivityLevelRepository = productivityLevelRepository;
        this.premiumLimitRepository = premiumLimitRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(PremiumCalculationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // имитация добавления админом типов кредитных продуктов и страховок
        premiumLimitRepository.save(new PremiumLimit(1L, new BigDecimal(100000)));
        insuranceRepository.save(new Insurance(1L, "Not", new BigDecimal(0), new BigDecimal(0)));
        insuranceRepository.save(new Insurance(2L, "Life", new BigDecimal(1), new BigDecimal("1.4")));
        insuranceRepository.save(new Insurance(3L, "Unemployment", new BigDecimal(1), new BigDecimal("1.6")));
        insuranceRepository.save(new Insurance(4L, "Combo", new BigDecimal(2), new BigDecimal(3)));
        productGroupRepository.save(new ProductGroup(1L, "Dns", new BigDecimal("0.002"), new BigDecimal(30), new BigDecimal(3000), TypeCredit.PointOfSale, new BigDecimal(0)));
        productGroupRepository.save(new ProductGroup(2L, "FURNITURE_FURS_AND_CLOTHING", new BigDecimal("0.0032"), new BigDecimal(30), new BigDecimal(3000), TypeCredit.PointOfSale, new BigDecimal(0)));
        productGroupRepository.save(new ProductGroup(3L, "COC_PREFERENTIAL", new BigDecimal("0.0009"), new BigDecimal(30), new BigDecimal(3000), TypeCredit.CashOnCard, new BigDecimal(100000)));
        criteriaBonusForFurRepository.save(new CriteriaBonusForFur(1L, new BigDecimal(750000), new BigDecimal(70), new BigDecimal(7500)));
        productivityLevelRepository.save(new ProductivityLevel(1L, "Level 0", BigDecimal.ZERO, 0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
        productivityLevelRepository.save(new ProductivityLevel(2L, "Level 1", new BigDecimal(7000), 16, new BigDecimal(500001), new BigDecimal(75), BigDecimal.ZERO));
        productivityLevelRepository.save(new ProductivityLevel(3L, "Level 2", new BigDecimal(10000), 21, new BigDecimal(650001), new BigDecimal(85), new BigDecimal(30)));
        productivityLevelRepository.save(new ProductivityLevel(4L, "Level 3", new BigDecimal(15000), 30, new BigDecimal(1000001), new BigDecimal(90), new BigDecimal(40)));
        productivityLevelRepository.save(new ProductivityLevel(5L, "Level 3", new BigDecimal(20000), 36, new BigDecimal(1500001), new BigDecimal(95), new BigDecimal(60)));
    }
}
