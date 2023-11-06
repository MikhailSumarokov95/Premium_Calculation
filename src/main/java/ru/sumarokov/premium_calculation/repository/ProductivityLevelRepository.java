package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sumarokov.premium_calculation.entity.ProductivityLevel;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductivityLevelRepository extends JpaRepository<ProductivityLevel, Long> {

    @Query("SELECT pl FROM ProductivityLevel pl WHERE pl.minCountCredits <= ?1 ORDER BY pl.id DESC LIMIT 1")
    Optional<ProductivityLevel> getCountCreditsLevel(Integer countCredits);

    @Query("SELECT pl FROM ProductivityLevel pl WHERE pl.minSumAmountCredits <= ?1 ORDER BY pl.id DESC LIMIT 1")
    Optional<ProductivityLevel> getSumAmountCreditsLevel(BigDecimal sumAmountCredits);

    @Query("SELECT pl FROM ProductivityLevel pl WHERE pl.minSms <= ?1 ORDER BY pl.id DESC LIMIT 1")
    Optional<ProductivityLevel> getSmsLevel(BigDecimal Sms);

    @Query("SELECT pl FROM ProductivityLevel pl WHERE pl.minInsurance <= ?1 ORDER BY pl.id DESC LIMIT 1")
    Optional<ProductivityLevel> getInsuranceLevel(BigDecimal Insurance);

    @Query("SELECT pl " +
            "FROM ProductivityLevel pl " +
            "WHERE (pl.minCountCredits <= ?1 OR pl.minSumAmountCredits <= ?2) AND pl.minSms <= ?3 AND pl.minInsurance <= ?4 " +
            "ORDER BY pl.id DESC " +
            "LIMIT 1")
    Optional<ProductivityLevel> getGeneralLevel(Integer countCredits, BigDecimal sumAmountCredits,
                                                BigDecimal sms, BigDecimal insurance);
}
