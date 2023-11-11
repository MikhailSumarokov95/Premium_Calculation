package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sumarokov.premium_calculation.entity.Credit;
import ru.sumarokov.premium_calculation.repository.CreditRepository;

import java.util.List;

@Service
public class CreditService {

    private final CreditRepository creditRepository;
    private final EfficiencyService efficiencyService;

    @Autowired
    public CreditService(CreditRepository creditRepository, EfficiencyService efficiencyService) {
        this.creditRepository = creditRepository;
        this.efficiencyService = efficiencyService;
    }

    public List<Credit> getCredits() {
        return creditRepository.findAll();
    }

    public Credit getCredit(Long id) {
        return creditRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void saveCredit(Credit credit) {
        creditRepository.save(credit);
        efficiencyService.calculateEfficiency();
    }

    @Transactional
    public void deleteCredit(Long id) {
        creditRepository.deleteById(id);
        efficiencyService.calculateEfficiency();
    }
}
