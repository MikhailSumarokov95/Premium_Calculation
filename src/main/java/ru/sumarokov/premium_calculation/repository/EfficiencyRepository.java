package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sumarokov.premium_calculation.entity.Efficiency;

import java.math.BigDecimal;
import java.util.Optional;

public interface EfficiencyRepository extends JpaRepository<Efficiency, Long> {

    Optional<Efficiency> findByUserId(Long id);

    @Query(value = "SELECT COALESCE(SUM(total_premium), 0) FROM efficiency",
            nativeQuery = true)
    BigDecimal getSumTotalPremiumSpecialists();
}
