package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.Credit;
import ru.sumarokov.premium_calculation.entity.Efficiency;
import ru.sumarokov.premium_calculation.entity.PreliminaryCreditResult;
import ru.sumarokov.premium_calculation.repository.PreliminaryCreditResultRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalculateService {

    private final PreliminaryCreditResultRepository preliminaryCreditResultRepository;

    @Autowired
    public CalculateService(PreliminaryCreditResultRepository preliminaryCreditResultRepository) {
        this.preliminaryCreditResultRepository = preliminaryCreditResultRepository;
    }

    public Efficiency calculateEfficiency(List<Credit> credits) {
        Efficiency efficiency = new Efficiency();

        List<PreliminaryCreditResult> preliminaryCreditResults = new ArrayList<>();
        for (Credit credit : credits)
            preliminaryCreditResults.add(calculatePreliminaryCreditResult(credit));
        preliminaryCreditResultRepository.saveAll(preliminaryCreditResults);

        return efficiency;
    }

    private PreliminaryCreditResult calculatePreliminaryCreditResult(Credit credit) {
        PreliminaryCreditResult preliminaryCreditResult = new PreliminaryCreditResult();

        Double insuranceVolume = credit.getAmount() * credit.getInsurance().getFactorInsuranceVolume();
        preliminaryCreditResult.setInsuranceVolume(insuranceVolume);

        Double insuranceBonus = credit.getAmount() * credit.getInsurance().getFactorInsuranceBonus();
        preliminaryCreditResult.setInsuranceBonus(insuranceBonus);

        Double creditPreviously;
        if (credit.getProductGroup().getIsCoc())
            if (credit.getProductGroup().getMinAmountForCalculatingCreditPremium() < credit.getAmount())
                creditPreviously = credit.getProductGroup().getFactorPremium() * credit.getAmount();
            else creditPreviously = 0d;
        else creditPreviously = credit.getProductGroup().getFactorPremium() * credit.getAmount()
                * credit.getTerm() * credit.getRate();

        if (creditPreviously > credit.getProductGroup().getMaxPremium())
            creditPreviously = credit.getProductGroup().getMaxPremium();

        if (creditPreviously < credit.getProductGroup().getMinPremium())
            creditPreviously = credit.getProductGroup().getMinPremium();

        preliminaryCreditResult.setCreditPreviously(creditPreviously);

        Double creditTotal;
        if (credit.getIsUsedSelfReject())
            creditTotal = preliminaryCreditResult.getCreditPreviously() / 2;
        else creditTotal = preliminaryCreditResult.getCreditPreviously();

        if (credit.getIsConsultantAvailability())
            creditTotal -= 50;

        preliminaryCreditResult.setCreditTotal(creditTotal);

        Double premium = preliminaryCreditResult.getCreditTotal() + preliminaryCreditResult.getInsuranceBonus();
        preliminaryCreditResult.setPremium(premium);

        preliminaryCreditResult.setCredit(credit);

        return preliminaryCreditResult;
    }
}
