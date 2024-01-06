package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sumarokov.premium_calculation.entity.FurResult;

import java.math.BigDecimal;
import java.util.Optional;

public interface FurResultRepository extends JpaRepository<FurResult, Long> {

    Optional<FurResult> findByUserId(Long id);

    @Query("SELECT COALESCE(SUM(fr.sumAmountCreditsCategoryFur), 0) FROM FurResult fr")
    BigDecimal getTotalVolumeCreditsForFurs();
}
