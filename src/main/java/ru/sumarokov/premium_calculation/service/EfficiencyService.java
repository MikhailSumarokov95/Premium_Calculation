package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.Efficiency;
import ru.sumarokov.premium_calculation.entity.PreliminaryCreditResult;
import ru.sumarokov.premium_calculation.repository.EfficiencyRepository;
import ru.sumarokov.premium_calculation.repository.PremiumLimitRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EfficiencyService {

    private final PreliminaryCreditResultService preliminaryCreditResultService;
    private final EfficiencyRepository efficiencyRepository;
    private final FurResultService furResultService;
    private final ProductivityResultService productivityResultService;
    private final PremiumLimitRepository premiumLimitRepository;
    private final InsuranceResultService insuranceResultService;

    @Autowired
    public EfficiencyService(PreliminaryCreditResultService preliminaryCreditResultService,
                             EfficiencyRepository efficiencyRepository,
                             FurResultService furResultService,
                             ProductivityResultService productivityResultService,
                             PremiumLimitRepository premiumLimitRepository,
                             InsuranceResultService insuranceResultService) {
        this.preliminaryCreditResultService = preliminaryCreditResultService;
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

        BigDecimal furBonus = furResultService.calculateFurResult().getBonus();
        efficiency.setFurBonus(furBonus);

        BigDecimal totalProductivity = calculateTotalProductivity();
        efficiency.setTotalProductivity(totalProductivity);

        BigDecimal premiumInsurance = insuranceResultService.calculateInsuranceResult().getTotalBonus();
        efficiency.setPremiumInsurance(premiumInsurance);

        BigDecimal totalPremium = calculateTotalPremium(premiumForCredit, furBonus,
                totalProductivity, premiumInsurance);
        efficiency.setTotalPremium(totalPremium);

        return efficiencyRepository.save(efficiency);
    }

    private BigDecimal calculatePreliminaryCreditResult() {
        List<PreliminaryCreditResult> preliminaryCreditResults =
                preliminaryCreditResultService.calculatePreliminaryCreditResults();
        return preliminaryCreditResults.stream()
                .map(PreliminaryCreditResult::getCreditTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalProductivity() {
        return productivityResultService
                .calculateProductivityResult()
                .getGeneralLevel()
                .getPremium();
    }

    private BigDecimal calculateTotalPremium(BigDecimal premiumForCredit,
                                             BigDecimal furBonus,
                                             BigDecimal totalProductivity,
                                             BigDecimal premiumInsurance) {
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
