package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sumarokov.premium_calculation.entity.InsuranceResult;

import java.math.BigDecimal;
import java.util.Optional;

public interface InsuranceResultRepository extends JpaRepository<InsuranceResult, Long> {

    Optional<InsuranceResult> findByUserId(Long id);


    @Query(value = "SELECT COALESCE(SUM(sum_insurance_volume), 0) FROM insurance_result",
            nativeQuery = true)
    BigDecimal getSumAllInsuranceVolume();

    @Query(value = "SELECT COALESCE(SUM(penetration)/COUNT(*), 0) FROM insurance_result",
            nativeQuery = true)
    BigDecimal getAverageInsurancePenetrationPercentage();
}
