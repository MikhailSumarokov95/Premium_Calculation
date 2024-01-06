package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sumarokov.premium_calculation.entity.Efficiency;

import java.math.BigDecimal;
import java.util.Optional;

public interface EfficiencyRepository extends JpaRepository<Efficiency, Long> {

    Optional<Efficiency> findByUserId(Long id);

    @Query("SELECT COALESCE(SUM(e.totalPremium), 0) FROM Efficiency e")
    BigDecimal getSumTotalPremiumSpecialists();
}
