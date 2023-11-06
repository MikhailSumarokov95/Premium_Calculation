package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.Efficiency;
import ru.sumarokov.premium_calculation.repository.EfficiencyRepository;
import ru.sumarokov.premium_calculation.repository.PreliminaryCreditResultRepository;
import ru.sumarokov.premium_calculation.repository.PremiumLimitRepository;

import java.math.BigDecimal;

@Service
public class EfficiencyService {

    private final PreliminaryCreditResultService preliminaryCreditResultService;
    private final PreliminaryCreditResultRepository preliminaryCreditResultRepository;
    private final EfficiencyRepository efficiencyRepository;
    private final FurResultService furResultService;
    private final ProductivityResultService productivityResultService;
    private final PremiumLimitRepository premiumLimitRepository;
    private final InsuranceResultService insuranceResultService;

    @Autowired
    public EfficiencyService(PreliminaryCreditResultService preliminaryCreditResultService,
                             PreliminaryCreditResultRepository preliminaryCreditResultRepository,
                             EfficiencyRepository efficiencyRepository,
                             FurResultService furResultService,
                             ProductivityResultService productivityResultService,
                             PremiumLimitRepository premiumLimitRepository,
                             InsuranceResultService insuranceResultService) {
        this.preliminaryCreditResultService = preliminaryCreditResultService;
        this.preliminaryCreditResultRepository = preliminaryCreditResultRepository;
        this.efficiencyRepository = efficiencyRepository;
        this.furResultService = furResultService;
        this.productivityResultService = productivityResultService;
        this.premiumLimitRepository = premiumLimitRepository;
        this.insuranceResultService = insuranceResultService;
    }

    public Efficiency calculateEfficiency() {
        Efficiency efficiency = efficiencyRepository.findById(1L).orElse(new Efficiency());

        preliminaryCreditResultService.calculatePreliminaryCreditResults();
        BigDecimal premiumForCredit = preliminaryCreditResultRepository.getSumCreditTotal();
        efficiency.setPremiumForCredits(premiumForCredit);

        BigDecimal furBonus = furResultService.culculateFurResult().getBonus();
        efficiency.setFurBonus(furBonus);

        BigDecimal totalProductivity = productivityResultService
                .calculateProductivityResult()
                .getGeneralLevel()
                .getPremium();
        efficiency.setTotalProductivity(totalProductivity);

        BigDecimal premiumInsurance = insuranceResultService.calculateInsuranceResult().getTotalBonus();
        efficiency.setPremiumInsurance(premiumInsurance);

        BigDecimal maxPremium = premiumLimitRepository
                .findById(1L)
                .orElseThrow()
                .getMaxTotalPremium();

        BigDecimal totalPremium = premiumForCredit
                .add(furBonus)
                .add(totalProductivity)
                .add(premiumInsurance)
                .min(maxPremium);
        efficiency.setTotalPremium(totalPremium);

        efficiencyRepository.save(efficiency);
        return efficiency;
    }
}
