package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.Efficiency;
import ru.sumarokov.premium_calculation.entity.PreliminaryCreditResult;
import ru.sumarokov.premium_calculation.entity.User;
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

    public BigDecimal getSumTotalPremiumSpecialists() {
        return efficiencyRepository.getSumTotalPremiumSpecialists();
    }

    public Efficiency getEfficiency(User user) {
        return efficiencyRepository.findByUserId(user.getId())
                .orElse(new Efficiency(user));
    }

    public Efficiency calculateEfficiency(User user) {
        Efficiency efficiency = getEfficiency(user);

        BigDecimal premiumForCredit = calculatePreliminaryCreditResult(user);
        efficiency.setPremiumForCredits(premiumForCredit);

        BigDecimal furBonus = furResultService.calculateFurResult(user).getBonus();
        efficiency.setFurBonus(furBonus);

        BigDecimal totalProductivity = calculateTotalProductivity(user);
        efficiency.setTotalProductivity(totalProductivity);

        BigDecimal premiumInsurance = insuranceResultService.calculateInsuranceResult(user).getTotalBonus();
        efficiency.setPremiumInsurance(premiumInsurance);

        BigDecimal totalPremium = calculateTotalPremium(premiumForCredit, furBonus,
                totalProductivity, premiumInsurance);
        efficiency.setTotalPremium(totalPremium);

        return efficiencyRepository.save(efficiency);
    }

    private BigDecimal calculatePreliminaryCreditResult(User user) {
        List<PreliminaryCreditResult> preliminaryCreditResults =
                preliminaryCreditResultService.calculatePreliminaryCreditResults(user);
        return preliminaryCreditResults.stream()
                .map(PreliminaryCreditResult::getCreditTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalProductivity(User user) {
        return productivityResultService
                .calculateProductivityResult(user)
                .getGeneralLevel()
                .getPremium();
    }

    private BigDecimal calculateTotalPremium(BigDecimal premiumForCredit,
                                             BigDecimal furBonus,
                                             BigDecimal totalProductivity,
                                             BigDecimal premiumInsurance) {
        BigDecimal maxPremium = premiumLimitRepository
                .findByIsActualTrue()
                .orElseThrow()
                .getMaxTotalPremium();

        BigDecimal totalPremium = premiumForCredit
                .add(furBonus)
                .add(totalProductivity)
                .add(premiumInsurance);

        return totalPremium.min(maxPremium);
    }
}
