package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.FurResult;
import ru.sumarokov.premium_calculation.repository.CreditRepository;
import ru.sumarokov.premium_calculation.repository.CriteriaBonusForFurRepository;
import ru.sumarokov.premium_calculation.repository.FurResultRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class FurResultService {

    private final CriteriaBonusForFurRepository criteriaBonusForFurRepository;
    private final FurResultRepository furResultRepository;
    private final CreditRepository creditRepository;

    @Autowired
    public FurResultService(CriteriaBonusForFurRepository criteriaBonusForFurRepository,
                            FurResultRepository furResultRepository,
                            CreditRepository creditRepository) {
        this.criteriaBonusForFurRepository = criteriaBonusForFurRepository;
        this.furResultRepository = furResultRepository;
        this.creditRepository = creditRepository;
    }

    public FurResult culculateFurResult() {
        FurResult furResult = furResultRepository.findById(1L).orElse(new FurResult());

        Integer countCreditsCategoryFur = creditRepository.getCountCreditsCategoryFur();
        if (countCreditsCategoryFur == 0) {
            furResult = new FurResult(BigDecimal.ZERO, 0, 0, BigDecimal.ZERO, BigDecimal.ZERO);
        } else {
            furResult.setCountCreditsCategoryFur(countCreditsCategoryFur);

            Integer countCreditsCategoryFurWithSms = creditRepository.getCountCreditsCategoryFurWithSms();
            furResult.setCountCreditsCategoryFurWithSms(countCreditsCategoryFurWithSms);

            BigDecimal shareCreditsCategoryFurWithSms =
                    BigDecimal.valueOf(countCreditsCategoryFurWithSms)
                            .divide(BigDecimal.valueOf(countCreditsCategoryFur), 5, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
            furResult.setShareCreditsCategoryFurWithSms(shareCreditsCategoryFurWithSms);

            BigDecimal sumAmountCreditsCategoryFur = creditRepository.getSumAmountCreditsCategoryFur();
            furResult.setSumAmountCreditsCategoryFur(sumAmountCreditsCategoryFur);

            BigDecimal bonus = criteriaBonusForFurRepository
                    .getBonus(sumAmountCreditsCategoryFur, shareCreditsCategoryFurWithSms)
                    .orElse(BigDecimal.ZERO);
            furResult.setBonus(bonus);
        }

        furResultRepository.save(furResult);
        return furResult;
    }
}