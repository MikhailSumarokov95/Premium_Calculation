package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.sumarokov.premium_calculation.entity.ProductivityLevel;

import java.util.List;

public interface ProductivityLevelRepository extends JpaRepository<ProductivityLevel, Long> {

    List<ProductivityLevel> findAllByOrderByPremiumDesc();

    List<ProductivityLevel> findAllByOrderByPremiumAsc();

    @Query("SELECT COUNT(*) > 0 FROM ProductivityLevel pl WHERE pl.id = :id and pl.isDefault = true")
    boolean existsByIdAndIsDefaultTrue(@Param("id") Long id);
}
