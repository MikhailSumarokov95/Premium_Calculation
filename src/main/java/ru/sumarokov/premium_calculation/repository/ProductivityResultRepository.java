package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.premium_calculation.entity.ProductivityResult;

import java.util.Optional;

public interface ProductivityResultRepository extends JpaRepository<ProductivityResult, Long> {

    Optional<ProductivityResult> findByUserId(Long id);
}
