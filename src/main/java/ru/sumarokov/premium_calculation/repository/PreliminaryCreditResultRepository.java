package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.premium_calculation.entity.PreliminaryCreditResult;

public interface PreliminaryCreditResultRepository extends JpaRepository<PreliminaryCreditResult, Long> {
}
