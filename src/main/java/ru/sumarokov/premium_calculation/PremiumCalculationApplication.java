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
public class PremiumCalculationApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(PremiumCalculationApplication.class, args);
    }

}
