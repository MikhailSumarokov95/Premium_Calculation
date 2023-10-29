package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.premium_calculation.entity.Insurance;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}
