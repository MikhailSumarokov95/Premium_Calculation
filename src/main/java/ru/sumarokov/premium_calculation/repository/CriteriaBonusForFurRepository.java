package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sumarokov.premium_calculation.entity.CriteriaBonusForFur;

import java.math.BigDecimal;
import java.util.Optional;

public interface CriteriaBonusForFurRepository extends JpaRepository<CriteriaBonusForFur, Long> {

    @Query("SELECT bonus FROM CriteriaBonusForFur cbff WHERE cbff.minSum <= ?1 AND cbff.minSms <= ?2")
    Optional<BigDecimal> getBonus(BigDecimal sumAmountCreditsCategoryFur,
                                 BigDecimal shareCreditsCategoryFurWithSms);
}