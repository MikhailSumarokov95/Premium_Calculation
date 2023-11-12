package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.premium_calculation.entity.ProductivityLevel;

import java.util.List;

public interface ProductivityLevelRepository extends JpaRepository<ProductivityLevel, Long> {

    List<ProductivityLevel> findAllByOrderByPremiumDesc();

    List<ProductivityLevel> findAllByOrderByPremiumAsc();
}
