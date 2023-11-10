package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.Credit;
import ru.sumarokov.premium_calculation.entity.PreliminaryCreditResult;
import ru.sumarokov.premium_calculation.helper.TypeCredit;
import ru.sumarokov.premium_calculation.repository.CreditRepository;
import ru.sumarokov.premium_calculation.repository.PreliminaryCreditResultRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PreliminaryCreditResultService {

    private final PreliminaryCreditResultRepository preliminaryCreditResultRepository;
    private final CreditRepository creditRepository;

    @Autowired
    public PreliminaryCreditResultService(PreliminaryCreditResultRepository preliminaryCreditResultRepository,
                                          CreditRepository creditRepository) {
        this.preliminaryCreditResultRepository = preliminaryCreditResultRepository;
        this.creditRepository = creditRepository;
    }

    public List<PreliminaryCreditResult> getPreliminaryCreditResults() {
        return preliminaryCreditResultRepository.findAll();
    }

    public List<PreliminaryCreditResult> calculatePreliminaryCreditResults() {
        List<Credit> credits = creditRepository.findAll();

        List<PreliminaryCreditResult> preliminaryCreditResults = credits
                .stream()
                .map(this::calculatePreliminaryCreditResult)
                .toList();
        preliminaryCreditResultRepository.saveAll(preliminaryCreditResults);
        return preliminaryCreditResults;
    }

    public PreliminaryCreditResult calculatePreliminaryCreditResult(Credit credit) {
        PreliminaryCreditResult preliminaryCreditResult = preliminaryCreditResultRepository.
                findById(credit.getId())
                .orElse(new PreliminaryCreditResult());

        preliminaryCreditResult.setInsuranceVolume(calculateFactorInsuranceVolume(credit));
        preliminaryCreditResult.setInsuranceBonus(calculateFactorInsuranceBonus(credit));
        preliminaryCreditResult.setCreditPreviously(calculateCreditPreviously(credit));
        preliminaryCreditResult.setCreditTotal(calculateCreditTotal(credit, preliminaryCreditResult));
        preliminaryCreditResult.setPremium(calculatePremium(preliminaryCreditResult));
        preliminaryCreditResult.setCredit(credit);
        return preliminaryCreditResult;
    }

    private BigDecimal calculateFactorInsuranceVolume(Credit credit) {
        BigDecimal factorInsuranceVolume = credit.getInsurance()
                .getFactorInsuranceVolume()
                .divide(BigDecimal.valueOf(100), 5, RoundingMode.HALF_UP);
        return credit.getAmount().multiply(factorInsuranceVolume);
    }

    private BigDecimal calculateFactorInsuranceBonus(Credit credit) {
        BigDecimal factorInsuranceBonus = credit.getInsurance()
                .getFactorInsuranceBonus()
                .divide(BigDecimal.valueOf(100), 5, RoundingMode.HALF_UP);
        return credit.getAmount().multiply(factorInsuranceBonus);
    }

    private BigDecimal calculateCreditPreviously(Credit credit) {
        BigDecimal creditPreviously;
        if (credit.getProductGroup().getTypeCredit() == TypeCredit.CASH_ON_CARD) {
            if (credit.getProductGroup()
                    .getMinAmountForCalculatingCreditPremium()
                    .compareTo(credit.getAmount()) < 0) {
                creditPreviously = credit.getProductGroup().getFactorPremium()
                        .multiply(credit.getAmount());
            } else {
                creditPreviously = new BigDecimal(0);
            }
        } else {
            creditPreviously = credit.getProductGroup().getFactorPremium()
                    .multiply(credit.getAmount())
                    .multiply(BigDecimal.valueOf(credit.getTerm()))
                    .multiply(credit.getRate());
        }

        if (creditPreviously.compareTo(credit.getProductGroup().getMaxPremium()) > 0) {
            creditPreviously = credit.getProductGroup().getMaxPremium();
        }

        if (creditPreviously.compareTo(credit.getProductGroup().getMinPremium()) < 0) {
            creditPreviously = credit.getProductGroup().getMinPremium();
        }
        return creditPreviously;
    }

    private BigDecimal calculateCreditTotal(Credit credit, PreliminaryCreditResult preliminaryCreditResult) {
        BigDecimal creditTotal;
        if (credit.getIsUsedSelfReject()) {
            creditTotal = preliminaryCreditResult.getCreditPreviously()
                    .divide(BigDecimal.valueOf(2), 5, RoundingMode.HALF_UP);
        } else {
            creditTotal = preliminaryCreditResult.getCreditPreviously();
        }

        if (credit.getIsConsultantAvailability()) {
            creditTotal = creditTotal.subtract(BigDecimal.valueOf(50));
        }
        return creditTotal;
    }

    private BigDecimal calculatePremium(PreliminaryCreditResult preliminaryCreditResult) {
        return preliminaryCreditResult
                .getCreditTotal()
                .add(preliminaryCreditResult.getInsuranceBonus());
    }
}
