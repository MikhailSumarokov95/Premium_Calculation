package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.InsuranceResult;
import ru.sumarokov.premium_calculation.entity.PreliminaryCreditResult;
import ru.sumarokov.premium_calculation.entity.User;
import ru.sumarokov.premium_calculation.repository.InsuranceResultRepository;
import ru.sumarokov.premium_calculation.repository.PreliminaryCreditResultRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class InsuranceResultService {

    private final InsuranceResultRepository insuranceResultRepository;
    private final PreliminaryCreditResultRepository preliminaryCreditResultRepository;
    private final AuthService authService;

    @Autowired
    public InsuranceResultService(InsuranceResultRepository insuranceResultRepository,
                                  PreliminaryCreditResultRepository preliminaryCreditResultRepository,
                                  AuthService authService) {
        this.insuranceResultRepository = insuranceResultRepository;
        this.preliminaryCreditResultRepository = preliminaryCreditResultRepository;
        this.authService = authService;
    }

    public InsuranceResult getInsuranceResult() {
        User user = authService.getUser();
        return insuranceResultRepository.findByUserId(user.getId())
                .orElse(new InsuranceResult(user));
    }

    public InsuranceResult calculateInsuranceResult() {
        InsuranceResult insuranceResult = getInsuranceResult();

        List<PreliminaryCreditResult> preliminaryCreditResults = preliminaryCreditResultRepository.findAll();
        insuranceResult.setTotalBonus(calculateSumInsuranceBonus(preliminaryCreditResults));
        BigDecimal sumInsuranceVolume = calculateSumInsuranceVolume(preliminaryCreditResults);
        insuranceResult.setSumInsuranceVolume(sumInsuranceVolume);
        insuranceResult.setPenetration(calculatePenetration(sumInsuranceVolume, preliminaryCreditResults));

        return insuranceResultRepository.save(insuranceResult);
    }

    private BigDecimal calculateSumInsuranceBonus(List<PreliminaryCreditResult> preliminaryCreditResults) {
        return preliminaryCreditResults.stream()
                .map(PreliminaryCreditResult::getInsuranceBonus)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateSumInsuranceVolume(List<PreliminaryCreditResult> preliminaryCreditResults) {
        return preliminaryCreditResults.stream()
                .map(PreliminaryCreditResult::getInsuranceVolume)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculatePenetration(BigDecimal sumInsuranceVolume, List<PreliminaryCreditResult> preliminaryCreditResults) {
        BigDecimal sumAmountCredits = preliminaryCreditResults.stream()
                .map(p -> p.getCredit().getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sumInsuranceVolume
                .divide(sumAmountCredits, 5, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100));
    }
}
