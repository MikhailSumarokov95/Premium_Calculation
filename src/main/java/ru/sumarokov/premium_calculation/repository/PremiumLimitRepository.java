package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.premium_calculation.entity.PremiumLimit;

import java.util.Optional;

public interface PremiumLimitRepository extends JpaRepository<PremiumLimit, Long> {

    Optional<PremiumLimit> findByIsActualTrue();
}
