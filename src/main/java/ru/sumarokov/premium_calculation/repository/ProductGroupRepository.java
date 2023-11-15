package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.premium_calculation.entity.ProductGroup;

import java.util.List;

public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {

    List<ProductGroup> findAllByOrderByFactorPremiumDesc();
}
