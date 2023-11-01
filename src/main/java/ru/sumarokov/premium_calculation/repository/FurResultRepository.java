package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.premium_calculation.entity.FurResult;

public interface FurResultRepository extends JpaRepository<FurResult, Long> {
}
