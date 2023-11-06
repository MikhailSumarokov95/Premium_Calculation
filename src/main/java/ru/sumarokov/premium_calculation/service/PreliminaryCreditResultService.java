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

    public List<PreliminaryCreditResult> calculatePreliminaryCreditResults() {
        return calculatePreliminaryCreditResults(creditRepository.findAll());
    }

    public List<PreliminaryCreditResult> calculatePreliminaryCreditResults(List<Credit> credits) {
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

        BigDecimal insuranceVolume = credit.getAmount().multiply(credit.getInsurance().getFactorInsuranceVolume());
        preliminaryCreditResult.setInsuranceVolume(insuranceVolume);

        BigDecimal insuranceBonus = credit.getAmount().multiply(credit.getInsurance().getFactorInsuranceBonus());
        preliminaryCreditResult.setInsuranceBonus(insuranceBonus);

        BigDecimal creditPreviously;
        if (credit.getProductGroup().getTypeCredit() == TypeCredit.CashOnCard) {
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

        preliminaryCreditResult.setCreditPreviously(creditPreviously);

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

        preliminaryCreditResult.setCreditTotal(creditTotal);

        BigDecimal premium = preliminaryCreditResult.getCreditTotal().add(preliminaryCreditResult.getInsuranceBonus());
        preliminaryCreditResult.setPremium(premium);

        preliminaryCreditResult.setCredit(credit);

        return preliminaryCreditResult;
    }
}
