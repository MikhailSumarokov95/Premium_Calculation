package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.Credit;
import ru.sumarokov.premium_calculation.entity.Efficiency;
import ru.sumarokov.premium_calculation.helper.ProductivityLevel;
import ru.sumarokov.premium_calculation.repository.EfficiencyRepository;
import ru.sumarokov.premium_calculation.repository.PreliminaryCreditResultRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EfficiencyService {

    private final PreliminaryCreditResultService preliminaryCreditResultService;
    private final PreliminaryCreditResultRepository preliminaryCreditResultRepository;
    private final EfficiencyRepository efficiencyRepository;

    @Autowired
    public EfficiencyService(PreliminaryCreditResultService preliminaryCreditResultService,
                             PreliminaryCreditResultRepository preliminaryCreditResultRepository,
                             EfficiencyRepository efficiencyRepository) {
        this.preliminaryCreditResultService = preliminaryCreditResultService;
        this.preliminaryCreditResultRepository = preliminaryCreditResultRepository;
        this.efficiencyRepository = efficiencyRepository;
    }

    public Efficiency calculateEfficiency() {
        Efficiency efficiency = efficiencyRepository.findById(1L).orElse(new Efficiency());

        preliminaryCreditResultService.calculatePreliminaryCreditResults();
        efficiency.setPremiumForCredits(preliminaryCreditResultRepository.getSumCreditTotal().orElseThrow());

        efficiency.setProductivityLevel(ProductivityLevel.LEVEL_ZERO);
        efficiency.setFurBonus(new BigDecimal(1));
        efficiency.setPremiumInsurance(new BigDecimal(1));
        efficiency.setPremiumForAdditionalProducts(new BigDecimal(1));
        efficiency.setTotalProductivity(new BigDecimal(1));
        efficiency.setTotalPremium(new BigDecimal(1));
        efficiencyRepository.save(efficiency);
        return efficiency;
    }
}
