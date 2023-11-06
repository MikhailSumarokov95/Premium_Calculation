package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.premium_calculation.entity.InsuranceResult;

public interface InsuranceResultRepository extends JpaRepository<InsuranceResult, Long> {
}
