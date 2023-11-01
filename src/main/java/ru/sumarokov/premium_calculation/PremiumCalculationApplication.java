package ru.sumarokov.premium_calculation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.sumarokov.premium_calculation.entity.CriteriaBonusForFur;
import ru.sumarokov.premium_calculation.entity.Insurance;
import ru.sumarokov.premium_calculation.entity.ProductGroup;
import ru.sumarokov.premium_calculation.repository.CriteriaBonusForFurRepository;
import ru.sumarokov.premium_calculation.repository.InsuranceRepository;
import ru.sumarokov.premium_calculation.repository.ProductGroupRepository;

import java.math.BigDecimal;

@SpringBootApplication
public class PremiumCalculationApplication implements CommandLineRunner {

    private InsuranceRepository insuranceRepository;
    private ProductGroupRepository productGroupRepository;
    private CriteriaBonusForFurRepository criteriaBonusForFurRepository;

    @Autowired
    public PremiumCalculationApplication(InsuranceRepository insuranceRepository,
                                         ProductGroupRepository productGroupRepository,
                                         CriteriaBonusForFurRepository criteriaBonusForFurRepository) {
        this.insuranceRepository = insuranceRepository;
        this.productGroupRepository = productGroupRepository;
        this.criteriaBonusForFurRepository = criteriaBonusForFurRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(PremiumCalculationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // имитация добавления админом типов кредитных продуктов и страховок
        insuranceRepository.save(new Insurance(1L, "Not", new BigDecimal(0), new BigDecimal(0)));
        insuranceRepository.save(new Insurance(2L, "Life", new BigDecimal(1), new BigDecimal("1.4")));
        insuranceRepository.save(new Insurance(3L, "Unemployment", new BigDecimal(1), new BigDecimal("1.6")));
        insuranceRepository.save(new Insurance(4L, "Combo", new BigDecimal(2), new BigDecimal(3)));
        productGroupRepository.save(new ProductGroup(1L, "Dns", new BigDecimal("0.002"), new BigDecimal(30), new BigDecimal(3000), false, new BigDecimal(0)));
        productGroupRepository.save(new ProductGroup(2L, "FURNITURE_FURS_AND_CLOTHING", new BigDecimal("0.0032"), new BigDecimal(30), new BigDecimal(3000), false, new BigDecimal(0)));
        productGroupRepository.save(new ProductGroup(3L, "COC_PREFERENTIAL", new BigDecimal("0.0009"), new BigDecimal(30), new BigDecimal(3000), true, new BigDecimal(100000)));
        criteriaBonusForFurRepository.save(new CriteriaBonusForFur(1L, new BigDecimal(750000), new BigDecimal(70), new BigDecimal(7500)));
    }
}
