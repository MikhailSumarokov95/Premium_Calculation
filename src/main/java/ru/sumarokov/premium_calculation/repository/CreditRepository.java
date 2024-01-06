package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sumarokov.premium_calculation.entity.Credit;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CreditRepository extends JpaRepository<Credit, Long> {

    List<Credit> findByUserId(Long user_id);

    List<Credit> findByUserIdAndIsFurTrue(Long user_id);

    List<Credit> findByUserIdOrderByIdAsc(Long user_id);

    Optional<Credit> findByIdAndUserId(Long id, Long user_id);

    Boolean existsByIdAndUserId(Long id, Long user_id);

    @Query("SELECT COALESCE(SUM(c.amount), 0) FROM Credit c")
    BigDecimal getSumAmountCredits();
}
