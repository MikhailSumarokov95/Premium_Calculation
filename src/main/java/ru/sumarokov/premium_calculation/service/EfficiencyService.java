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

    @Autowired
    public EfficiencyService(PreliminaryCreditResultService preliminaryCreditResultService,
                             PreliminaryCreditResultRepository preliminaryCreditResultRepository,
                             EfficiencyRepository efficiencyRepository,
                             FurResultService furResultService,
                             ProductivityResultService productivityResultService,
                             PremiumLimitRepository premiumLimitRepository) {
        this.preliminaryCreditResultService = preliminaryCreditResultService;
        this.preliminaryCreditResultRepository = preliminaryCreditResultRepository;
        this.efficiencyRepository = efficiencyRepository;
        this.furResultService = furResultService;
        this.productivityResultService = productivityResultService;
        this.premiumLimitRepository = premiumLimitRepository;
    }

    public Efficiency calculateEfficiency() {
        Efficiency efficiency = efficiencyRepository.findById(1L).orElse(new Efficiency());

        preliminaryCreditResultService.calculatePreliminaryCreditResults();
        BigDecimal premiumForCredit = preliminaryCreditResultRepository.getSumCreditTotal().orElseThrow();
        efficiency.setPremiumForCredits(premiumForCredit);

        BigDecimal furBonus = furResultService.culculateFurResult().getBonus();
        efficiency.setFurBonus(furBonus);

        BigDecimal totalProductivity = productivityResultService
                .calculateProductivityResult()
                .getGeneralLevel()
                .getPremium();
        efficiency.setTotalProductivity(totalProductivity);

        BigDecimal premiumInsurance = new BigDecimal(1); //
        efficiency.setPremiumInsurance(premiumInsurance);

        BigDecimal premiumForAdditionalProducts = new BigDecimal(1); //
        efficiency.setPremiumForAdditionalProducts(premiumForAdditionalProducts);

        BigDecimal maxPremium = premiumLimitRepository
                .findById(1L)
                .orElseThrow()
                .getMaxTotalPremium();

        BigDecimal totalPremium = premiumForCredit
                .add(furBonus)
                .add(totalProductivity)
                .add(premiumInsurance)
                .add(premiumForAdditionalProducts)
                .min(maxPremium);
        efficiency.setTotalPremium(totalPremium);

        efficiencyRepository.save(efficiency);
        return efficiency;
    }
}
