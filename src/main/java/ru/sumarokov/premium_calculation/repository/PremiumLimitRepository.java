package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.premium_calculation.entity.PremiumLimit;

public interface PremiumLimitRepository extends JpaRepository<PremiumLimit, Long> {
}
