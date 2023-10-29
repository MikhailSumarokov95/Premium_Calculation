package ru.sumarokov.premium_calculation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.sumarokov.premium_calculation.entity.Insurance;
import ru.sumarokov.premium_calculation.entity.ProductGroup;
import ru.sumarokov.premium_calculation.repository.InsuranceRepository;
import ru.sumarokov.premium_calculation.repository.ProductGroupRepository;

@SpringBootApplication
public class PremiumCalculationApplication implements CommandLineRunner {

    private InsuranceRepository insuranceRepository;
    private ProductGroupRepository productGroupRepository;

    @Autowired
    public PremiumCalculationApplication(InsuranceRepository insuranceRepository,
                                         ProductGroupRepository productGroupRepository) {
        this.insuranceRepository = insuranceRepository;
        this.productGroupRepository = productGroupRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(PremiumCalculationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // имитация добавления админом типов кредитных продуктов и страховок
        insuranceRepository.save(new Insurance(1L, "Not", 0d, 0d));
        insuranceRepository.save(new Insurance(2L, "Life", 1d, 1.4d));
        insuranceRepository.save(new Insurance(3L, "Unemployment", 1d, 1.6d));
        insuranceRepository.save(new Insurance(4L, "Combo", 2d, 3d));
        productGroupRepository.save(new ProductGroup(1L, "Dns", 0.002d, 30d, 3000d, false, 0d));
        productGroupRepository.save(new ProductGroup(2L, "FURNITURE_FURS_AND_CLOTHING", 0.0032d, 30d, 3000d, false, 0d));
        productGroupRepository.save(new ProductGroup(3L, "COC_PREFERENTIAL", 0.0009d, 30d, 3000d, true, 100000d));
    }
}
