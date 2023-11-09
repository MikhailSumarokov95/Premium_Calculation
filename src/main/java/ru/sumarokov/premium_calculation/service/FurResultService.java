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

    public FurResult getFurResult() {
        return furResultRepository.findById(1L).orElse(new FurResult());
    }

    public FurResult calculateFurResult() {
        FurResult furResult = furResultRepository.findById(1L).orElse(new FurResult());

        Integer countCreditsCategoryFur = calculateCreditsCategoryFur();
        if (countCreditsCategoryFur == 0) {
            furResult = new FurResult(BigDecimal.ZERO, 0, 0, BigDecimal.ZERO, BigDecimal.ZERO);
        } else {
            furResult.setCountCreditsCategoryFur(countCreditsCategoryFur);

            Integer countCreditsCategoryFurWithSms = calculateCountCreditsCategoryFurWithSms();
            furResult.setCountCreditsCategoryFurWithSms(countCreditsCategoryFurWithSms);

            BigDecimal penetrationSmsCreditsCategoryFur =
                    calculatePenetrationSmsCreditsCategoryFur(countCreditsCategoryFur, countCreditsCategoryFurWithSms);
            furResult.setPenetrationSmsCreditsCategoryFur(penetrationSmsCreditsCategoryFur);

            BigDecimal sumAmountCreditsCategoryFur = calculateSumAmountCreditsCategoryFur();
            furResult.setSumAmountCreditsCategoryFur(sumAmountCreditsCategoryFur);

            BigDecimal bonus = calculateBonus(sumAmountCreditsCategoryFur, penetrationSmsCreditsCategoryFur);
            furResult.setBonus(bonus);
        }
        furResultRepository.save(furResult);
        return furResult;
    }

    private Integer calculateCreditsCategoryFur() {
        return creditRepository.getCountCreditsCategoryFur();
    }

    private Integer calculateCountCreditsCategoryFurWithSms() {
        return creditRepository.getCountCreditsCategoryFurWithSms();
    }

    private BigDecimal calculatePenetrationSmsCreditsCategoryFur(Integer countCreditsCategoryFur,
                                                                 Integer countCreditsCategoryFurWithSms) {
        return BigDecimal.valueOf(countCreditsCategoryFurWithSms)
                .divide(BigDecimal.valueOf(countCreditsCategoryFur), 5, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    private BigDecimal calculateSumAmountCreditsCategoryFur() {
        return creditRepository.getSumAmountCreditsCategoryFur();
    }

    private BigDecimal calculateBonus(BigDecimal sumAmountCreditsCategoryFur,
                                      BigDecimal penetrationSmsCreditsCategoryFur) {
        return criteriaBonusForFurRepository
                .getBonus(sumAmountCreditsCategoryFur, penetrationSmsCreditsCategoryFur)
                .orElse(BigDecimal.ZERO);
    }
}