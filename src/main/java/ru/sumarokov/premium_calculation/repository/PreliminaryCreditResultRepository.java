package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sumarokov.premium_calculation.entity.PreliminaryCreditResult;

import java.math.BigDecimal;

public interface PreliminaryCreditResultRepository extends JpaRepository<PreliminaryCreditResult, Long> {

    @Query("SELECT SUM(pcr.insuranceBonus) FROM PreliminaryCreditResult pcr")
    BigDecimal getSumInsuranceBonus();
}
