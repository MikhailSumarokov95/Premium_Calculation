package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sumarokov.premium_calculation.entity.Credit;

import java.math.BigDecimal;
import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {

    List<Credit> findByIsFurTrue();

    @Query("SELECT SUM(c.amount) FROM Credit c")
    BigDecimal getSumAmountCredits();

    @Query("SELECT COUNT(c) FROM Credit c")
    Integer getCountCredits();

    @Query("SELECT COUNT(c) FROM Credit c WHERE c.isConnectedSms = true")
    Integer getCountCreditsWithSms();
}
