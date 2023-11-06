package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sumarokov.premium_calculation.entity.Credit;

import java.math.BigDecimal;
import java.util.Optional;

public interface CreditRepository extends JpaRepository<Credit, Long> {

    @Query("SELECT COUNT(c) FROM Credit c WHERE c.isFur = true")
    Integer getCountCreditsCategoryFur();

    @Query("SELECT COUNT(c) FROM Credit c WHERE c.isFur = true AND c.isConnectedSms = true")
    Integer getCountCreditsCategoryFurWithSms();

    @Query("SELECT SUM(c.amount) FROM Credit c WHERE c.isFur = true")
    BigDecimal getSumAmountCreditsCategoryFur();

    @Query("SELECT SUM(c.amount) FROM Credit c")
    BigDecimal getSumAmountCredits();

    @Query("SELECT COUNT(c) FROM Credit c")
    Integer getCountCredits();

    @Query("SELECT COUNT(c) FROM Credit c WHERE c.isConnectedSms = true")
    Integer getCountCreditsWithSms();
}
