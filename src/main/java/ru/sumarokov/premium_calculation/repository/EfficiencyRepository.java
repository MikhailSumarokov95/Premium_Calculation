package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.premium_calculation.entity.Efficiency;

public interface EfficiencyRepository extends JpaRepository<Efficiency, Long> {
}
