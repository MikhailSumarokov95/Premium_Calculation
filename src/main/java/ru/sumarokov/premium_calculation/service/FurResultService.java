package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.Credit;
import ru.sumarokov.premium_calculation.entity.FurResult;
import ru.sumarokov.premium_calculation.repository.CreditRepository;
import ru.sumarokov.premium_calculation.repository.CriteriaBonusForFurRepository;
import ru.sumarokov.premium_calculation.repository.FurResultRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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

        List<Credit> creditsCategoryFur = creditRepository.findByIsFurTrue();
        Long countCreditsCategoryFur = (long) creditsCategoryFur.size();
        if (countCreditsCategoryFur == 0L) {
            furResult = new FurResult(BigDecimal.ZERO, 0L, 0L, BigDecimal.ZERO, BigDecimal.ZERO);
        } else {
            furResult.setCountCreditsCategoryFur(countCreditsCategoryFur);

            Long countCreditsCategoryFurWithSms = calculateCountCreditsCategoryFurWithSms(creditsCategoryFur);
            furResult.setCountCreditsCategoryFurWithSms(countCreditsCategoryFurWithSms);

            BigDecimal penetrationSmsCreditsCategoryFur =
                    calculatePenetrationSmsCreditsCategoryFur(countCreditsCategoryFur, countCreditsCategoryFurWithSms);
            furResult.setPenetrationSmsCreditsCategoryFur(penetrationSmsCreditsCategoryFur);

            BigDecimal sumAmountCreditsCategoryFur = calculateSumAmountCreditsCategoryFur(creditsCategoryFur);
            furResult.setSumAmountCreditsCategoryFur(sumAmountCreditsCategoryFur);

            BigDecimal bonus = calculateBonus(sumAmountCreditsCategoryFur, penetrationSmsCreditsCategoryFur);
            furResult.setBonus(bonus);
        }
        return furResultRepository.save(furResult);
    }

    private Long calculateCountCreditsCategoryFurWithSms(List<Credit> creditsCategoryFur) {
        return creditsCategoryFur.stream()
                .filter(Credit::getIsConnectedSms)
                .count();
    }

    private BigDecimal calculatePenetrationSmsCreditsCategoryFur(Long countCreditsCategoryFur,
                                                                 Long countCreditsCategoryFurWithSms) {
        return BigDecimal.valueOf(countCreditsCategoryFurWithSms)
                .divide(BigDecimal.valueOf(countCreditsCategoryFur), 5, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    private BigDecimal calculateSumAmountCreditsCategoryFur(List<Credit> creditsCategoryFur) {
        return creditsCategoryFur.stream()
                .map(Credit::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateBonus(BigDecimal sumAmountCreditsCategoryFur,
                                      BigDecimal penetrationSmsCreditsCategoryFur) {
        return criteriaBonusForFurRepository
                .getBonus(sumAmountCreditsCategoryFur, penetrationSmsCreditsCategoryFur)
                .orElse(BigDecimal.ZERO);
    }
}