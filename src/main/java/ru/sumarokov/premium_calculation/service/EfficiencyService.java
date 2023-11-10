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

    public Efficiency getEfficiency() {
        return efficiencyRepository.findById(1L).orElse(new Efficiency());
    }

    public Efficiency calculateEfficiency() {
        Efficiency efficiency = efficiencyRepository.findById(1L).orElse(new Efficiency());

        BigDecimal premiumForCredit = calculatePreliminaryCreditResult();
        efficiency.setPremiumForCredits(premiumForCredit);

        BigDecimal furBonus = calculateFurBonus();
        efficiency.setFurBonus(furBonus);

        BigDecimal totalProductivity = calculateTotalProductivity();
        efficiency.setTotalProductivity(totalProductivity);

        BigDecimal premiumInsurance = calculatePremiumInsurance();
        efficiency.setPremiumInsurance(premiumInsurance);

        BigDecimal totalPremium = calculateTotalPremium(premiumForCredit, furBonus,
                totalProductivity, premiumInsurance);
        efficiency.setTotalPremium(totalPremium);

        efficiencyRepository.save(efficiency);
        return efficiency;
    }

    private BigDecimal calculatePreliminaryCreditResult() {
        preliminaryCreditResultService.calculatePreliminaryCreditResults();
        //TODO: без БД
        return preliminaryCreditResultRepository.getSumCreditTotal();
    }

    private BigDecimal calculateFurBonus() {
        return furResultService.calculateFurResult().getBonus();
    }

    private BigDecimal calculateTotalProductivity() {
        return productivityResultService
                .calculateProductivityResult()
                .getGeneralLevel()
                .getPremium();
    }

    private BigDecimal calculatePremiumInsurance() {
        return insuranceResultService.calculateInsuranceResult().getTotalBonus();
    }

    private BigDecimal calculateTotalPremium(BigDecimal premiumForCredit, BigDecimal furBonus,
                                             BigDecimal totalProductivity, BigDecimal premiumInsurance) {
        BigDecimal maxPremium = premiumLimitRepository
                .findById(1L)
                .orElseThrow()
                .getMaxTotalPremium();

        BigDecimal totalPremium = premiumForCredit
                .add(furBonus)
                .add(totalProductivity)
                .add(premiumInsurance);

        return totalPremium.min(maxPremium);
    }
}
