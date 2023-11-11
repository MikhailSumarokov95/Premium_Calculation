package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sumarokov.premium_calculation.entity.ProductivityLevel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductivityLevelRepository extends JpaRepository<ProductivityLevel, Long> {

    List<ProductivityLevel> findAllByOrderByPremiumDesc();

    List<ProductivityLevel> findAllByOrderByPremiumAsc();

    @Query("SELECT pl FROM ProductivityLevel pl WHERE pl.minCountCredits <= ?1 ORDER BY pl.id DESC LIMIT 1")
    Optional<ProductivityLevel> getCountCreditsLevel(Integer countCredits);

    @Query("SELECT pl FROM ProductivityLevel pl WHERE pl.minSumAmountCredits <= ?1 ORDER BY pl.id DESC LIMIT 1")
    Optional<ProductivityLevel> getSumAmountCreditsLevel(BigDecimal sumAmountCredits);

    @Query("SELECT pl FROM ProductivityLevel pl WHERE pl.minPenetrationSms <= ?1 ORDER BY pl.id DESC LIMIT 1")
    Optional<ProductivityLevel> getSmsPenetrationLevel(BigDecimal Sms);

    @Query("SELECT pl FROM ProductivityLevel pl WHERE pl.minPenetrationInsurance <= ?1 ORDER BY pl.id DESC LIMIT 1")
    Optional<ProductivityLevel> getInsurancePenetrationLevel(BigDecimal Insurance);

    @Query("SELECT pl " +
            "FROM ProductivityLevel pl " +
            "WHERE (pl.minCountCredits <= ?1 OR pl.minSumAmountCredits <= ?2) " +
            "AND pl.minPenetrationSms <= ?3 AND pl.minPenetrationInsurance <= ?4 " +
            "ORDER BY pl.id DESC " +
            "LIMIT 1")
    Optional<ProductivityLevel> getGeneralLevel(Integer countCredits, BigDecimal sumAmountCredits,
                                                BigDecimal sms, BigDecimal insurance);
}
