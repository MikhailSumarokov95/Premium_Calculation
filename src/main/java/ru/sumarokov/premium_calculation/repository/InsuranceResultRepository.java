package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.premium_calculation.entity.InsuranceResult;

import java.util.Optional;

public interface InsuranceResultRepository extends JpaRepository<InsuranceResult, Long> {

    Optional<InsuranceResult> findByUserId(Long id);
}
