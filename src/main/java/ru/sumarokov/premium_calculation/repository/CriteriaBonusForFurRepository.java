package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sumarokov.premium_calculation.entity.CriteriaBonusForFur;

import java.util.List;

public interface CriteriaBonusForFurRepository extends JpaRepository<CriteriaBonusForFur, Long> {

    List<CriteriaBonusForFur> findAllByOrderByBonusDesc();
}
