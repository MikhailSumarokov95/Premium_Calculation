package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.InsuranceResult;
import ru.sumarokov.premium_calculation.repository.CreditRepository;
import ru.sumarokov.premium_calculation.repository.InsuranceResultRepository;
import ru.sumarokov.premium_calculation.repository.PreliminaryCreditResultRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class InsuranceResultService {

    private final InsuranceResultRepository insuranceResultRepository;
    private final PreliminaryCreditResultRepository preliminaryCreditResultRepository;
    private final CreditRepository creditRepository;

    @Autowired
    public InsuranceResultService(InsuranceResultRepository insuranceResultRepository,
                                  PreliminaryCreditResultRepository preliminaryCreditResultRepository,
                                  CreditRepository creditRepository) {
        this.insuranceResultRepository = insuranceResultRepository;
        this.preliminaryCreditResultRepository = preliminaryCreditResultRepository;
        this.creditRepository = creditRepository;
    }

    public InsuranceResult getInsuranceResult() {
        return insuranceResultRepository.findById(1L).orElse(new InsuranceResult());
    }

    public InsuranceResult calculateInsuranceResult() {
        InsuranceResult insuranceResult = insuranceResultRepository
                .findById(1L)
                .orElse(new InsuranceResult());

        insuranceResult.setTotalBonus(calculateTotalBonus());
        insuranceResult.setPenetration(calculatePenetration());
        return insuranceResultRepository.save(insuranceResult);
    }

    private BigDecimal calculateTotalBonus() {
        return preliminaryCreditResultRepository.getSumInsuranceBonus();
    }

    private BigDecimal calculatePenetration() {
        BigDecimal sumAmountCredits = creditRepository.getSumAmountCredits();
        BigDecimal sumInsuranceBonus = preliminaryCreditResultRepository.getSumInsuranceBonus();
        return sumInsuranceBonus
                .divide(sumAmountCredits, 5, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100));
    }
}
