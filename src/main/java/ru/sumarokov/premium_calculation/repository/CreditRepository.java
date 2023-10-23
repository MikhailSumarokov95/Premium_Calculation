package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.premium_calculation.entity.Credit;

public interface CreditRepository extends JpaRepository<Credit, Long> {
}
