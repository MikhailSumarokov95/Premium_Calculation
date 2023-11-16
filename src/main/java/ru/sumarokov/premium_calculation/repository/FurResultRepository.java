package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.premium_calculation.entity.FurResult;

import java.util.Optional;

public interface FurResultRepository extends JpaRepository<FurResult, Long> {

    Optional<FurResult> findByUserId(Long id);
}
