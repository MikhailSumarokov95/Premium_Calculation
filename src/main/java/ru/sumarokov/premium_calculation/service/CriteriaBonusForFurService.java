package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.CriteriaBonusForFur;
import ru.sumarokov.premium_calculation.exception.EntityNotFoundException;
import ru.sumarokov.premium_calculation.repository.CriteriaBonusForFurRepository;

import java.util.List;

@Service
public class CriteriaBonusForFurService {

    private final CriteriaBonusForFurRepository criteriaBonusForFurRepository;

    @Autowired
    public CriteriaBonusForFurService(CriteriaBonusForFurRepository criteriaBonusForFurRepository) {
        this.criteriaBonusForFurRepository = criteriaBonusForFurRepository;
    }

    public List<CriteriaBonusForFur> getCriteriasBonusForFur() {
        return criteriaBonusForFurRepository.findAllByOrderByBonusDesc();
    }

    public CriteriaBonusForFur getCriteriaBonusForFur(Long id) {
        return criteriaBonusForFurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CriteriaBonusForFur.class, id));
    }

    public void saveCriteriaBonusForFur(CriteriaBonusForFur criteriaBonusForFur) {
        criteriaBonusForFurRepository.save(criteriaBonusForFur);
    }

    public void deleteCriteriaBonusForFur(Long id) {
        if (!criteriaBonusForFurRepository.existsById(id)) {
            throw new EntityNotFoundException(CriteriaBonusForFur.class, id);
        }
        criteriaBonusForFurRepository.deleteById(id);
    }
}
