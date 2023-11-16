package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.premium_calculation.entity.Efficiency;

import java.util.Optional;

public interface EfficiencyRepository extends JpaRepository<Efficiency, Long> {

    Optional<Efficiency> findByUserId(Long id);
}
