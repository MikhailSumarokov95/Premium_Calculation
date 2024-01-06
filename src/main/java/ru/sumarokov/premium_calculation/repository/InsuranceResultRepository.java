package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sumarokov.premium_calculation.entity.InsuranceResult;

import java.math.BigDecimal;
import java.util.Optional;

public interface InsuranceResultRepository extends JpaRepository<InsuranceResult, Long> {

    Optional<InsuranceResult> findByUserId(Long id);


    @Query("SELECT COALESCE(SUM(ir.sumInsuranceVolume), 0) FROM InsuranceResult ir")
    BigDecimal getTotalInsuredVolumeCredits();

    @Query("SELECT COALESCE(SUM(ir.penetration)/COUNT(*), 0) FROM InsuranceResult ir")
    BigDecimal getAverageInsurancePenetrationPercentage();
}
