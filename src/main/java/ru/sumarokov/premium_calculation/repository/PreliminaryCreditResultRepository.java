package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.premium_calculation.entity.PreliminaryCreditResult;

import java.util.List;

public interface PreliminaryCreditResultRepository extends JpaRepository<PreliminaryCreditResult, Long> {

    List<PreliminaryCreditResult> findByCreditUserIdOrderByCreditIdAsc(Long id);

    List<PreliminaryCreditResult> findByCreditUserId(Long id);
}
