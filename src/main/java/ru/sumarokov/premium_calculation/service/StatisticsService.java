package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.helper.Role;
import ru.sumarokov.premium_calculation.repository.*;

import java.math.BigDecimal;

@Service
public class StatisticsService {

    private final CreditRepository creditRepository;
    private final UserRepository userRepository;
    private final EfficiencyRepository efficiencyRepository;
    private final FurResultRepository furResultRepository;
    private final InsuranceResultRepository insuranceResultRepository;

    @Autowired
    public StatisticsService(CreditRepository creditRepository,
                             UserRepository userRepository,
                             EfficiencyRepository efficiencyRepository,
                             FurResultRepository furResultRepository,
                             InsuranceResultRepository insuranceResultRepository) {
        this.creditRepository = creditRepository;
        this.userRepository = userRepository;
        this.efficiencyRepository = efficiencyRepository;
        this.furResultRepository = furResultRepository;
        this.insuranceResultRepository = insuranceResultRepository;
    }

    public BigDecimal getSumAmountCredits() {
        return creditRepository.getSumAmountCredits();
    }

    public Long getCountCreditSpecialist() {
        return userRepository.getSumUsersSelectedRole(Role.ROLE_CREDIT_SPECIALIST);
    }

    public BigDecimal getSumTotalPremiumSpecialists() {
        return efficiencyRepository.getSumTotalPremiumSpecialists();
    }

    public BigDecimal getTotalVolumeCreditsForFurs() {
        return furResultRepository.getTotalVolumeCreditsForFurs();
    }

    public BigDecimal getTotalInsuredVolumeCredits() {
        return insuranceResultRepository.getTotalInsuredVolumeCredits();
    }

    public BigDecimal getAverageInsurancePenetrationPercentage() {
        return insuranceResultRepository.getAverageInsurancePenetrationPercentage();
    }
}
