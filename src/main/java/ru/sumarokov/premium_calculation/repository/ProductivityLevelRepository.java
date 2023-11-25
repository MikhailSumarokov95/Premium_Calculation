package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sumarokov.premium_calculation.entity.ProductivityLevel;

import java.util.List;

public interface ProductivityLevelRepository extends JpaRepository<ProductivityLevel, Long> {

    List<ProductivityLevel> findAllByOrderByPremiumDesc();

    List<ProductivityLevel> findAllByOrderByPremiumAsc();

    @Query(value = "SELECT COUNT(*) > 0 FROM productivity_level WHERE id = ? and is_default = true",
            nativeQuery = true)
    boolean existsByIdAndIsDefaultTrue(Long id);
}
