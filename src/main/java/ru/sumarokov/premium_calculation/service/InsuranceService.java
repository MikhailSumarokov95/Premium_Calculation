package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.CriteriaBonusForFur;
import ru.sumarokov.premium_calculation.entity.Insurance;
import ru.sumarokov.premium_calculation.exception.EntityNotFoundException;
import ru.sumarokov.premium_calculation.repository.InsuranceRepository;

import java.util.List;


@Service
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;

    @Autowired
    public InsuranceService(InsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }

    public List<Insurance> getInsurances() {
        return insuranceRepository.findAllByOrderByFactorInsuranceBonusDesc();
    }

    public Insurance getInsurance(Long id) {
        return insuranceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Insurance.class, id));
    }

    public void saveInsurance(Insurance insurance) {
        insuranceRepository.save(insurance);
    }

    public void deleteInsurance(Long id) {
        if (!insuranceRepository.existsById(id)) {
            throw new EntityNotFoundException(Insurance.class, id);
        }
        insuranceRepository.deleteById(id);
    }
}
