package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.Credit;
import ru.sumarokov.premium_calculation.entity.CriteriaBonusForFur;
import ru.sumarokov.premium_calculation.entity.FurResult;
import ru.sumarokov.premium_calculation.entity.User;
import ru.sumarokov.premium_calculation.repository.CreditRepository;
import ru.sumarokov.premium_calculation.repository.CriteriaBonusForFurRepository;
import ru.sumarokov.premium_calculation.repository.FurResultRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
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

    public BigDecimal getSumAmountAllCreditsCategoryFur() {
        return furResultRepository.getSumAmountAllCreditsCategoryFur();
    }

    public FurResult getFurResult(User user) {
        return furResultRepository.findByUserId(user.getId())
                .orElse(new FurResult(user));
    }

    public FurResult calculateFurResult(User user) {
        FurResult furResult = getFurResult(user);

        List<Credit> creditsCategoryFur = creditRepository.findByUserIdAndIsFurTrue(user.getId());
        Long countCreditsCategoryFur = (long) creditsCategoryFur.size();
        if (countCreditsCategoryFur == 0L) {
            furResult.setCountCreditsCategoryFur(0L);
            furResult.setCountCreditsCategoryFurWithSms(0L);
            furResult.setPenetrationSmsCreditsCategoryFur(BigDecimal.ZERO);
            furResult.setSumAmountCreditsCategoryFur(BigDecimal.ZERO);
            furResult.setBonus(BigDecimal.ZERO);
        } else {
            furResult.setCountCreditsCategoryFur(countCreditsCategoryFur);

            Long countCreditsCategoryFurWithSms = calculateCountCreditsCategoryFurWithSms(creditsCategoryFur);
            furResult.setCountCreditsCategoryFurWithSms(countCreditsCategoryFurWithSms);

            BigDecimal penetrationSmsCreditsCategoryFur =
                    calculatePenetrationSmsCreditsCategoryFur(countCreditsCategoryFur, countCreditsCategoryFurWithSms);
            furResult.setPenetrationSmsCreditsCategoryFur(penetrationSmsCreditsCategoryFur);

            BigDecimal sumAmountCreditsCategoryFur = calculateSumAmountCreditsCategoryFur(creditsCategoryFur);
            furResult.setSumAmountCreditsCategoryFur(sumAmountCreditsCategoryFur);

            BigDecimal bonus = calculateBonus(sumAmountCreditsCategoryFur,
                    penetrationSmsCreditsCategoryFur, criteriaBonusForFurRepository.findAll());
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
                                      BigDecimal penetrationSmsCreditsCategoryFur,
                                      List<CriteriaBonusForFur> criteriaBonusForFurs) {
        CriteriaBonusForFur criteriaBonusForFurZeroBonus = new CriteriaBonusForFur();
        criteriaBonusForFurZeroBonus.setBonus(BigDecimal.ZERO);

        return criteriaBonusForFurs
                .stream()
                .sorted(Comparator.comparing(CriteriaBonusForFur::getBonus).reversed())
                .filter(p -> p.getMinSum().compareTo(sumAmountCreditsCategoryFur) <= 0
                        && p.getMinSms().compareTo(penetrationSmsCreditsCategoryFur) <= 0)
                .findFirst()
                .orElse(criteriaBonusForFurZeroBonus)
                .getBonus();
    }
}