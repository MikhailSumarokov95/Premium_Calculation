package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.Credit;
import ru.sumarokov.premium_calculation.entity.Efficiency;
import ru.sumarokov.premium_calculation.entity.FurResult;
import ru.sumarokov.premium_calculation.entity.User;
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
    private final AuthService authService;

    @Autowired
    public FurResultService(CriteriaBonusForFurRepository criteriaBonusForFurRepository,
                            FurResultRepository furResultRepository,
                            CreditRepository creditRepository,
                            AuthService authService) {
        this.criteriaBonusForFurRepository = criteriaBonusForFurRepository;
        this.furResultRepository = furResultRepository;
        this.creditRepository = creditRepository;
        this.authService = authService;
    }

    public FurResult getFurResult() {
        User user = authService.getUser();
        return furResultRepository.findByUserId(user.getId())
                .orElse(new FurResult(user));
    }

    public FurResult calculateFurResult() {
        FurResult furResult = getFurResult();

        List<Credit> creditsCategoryFur = creditRepository.findByUserIdAndIsFurTrue(authService.getUser().getId());
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