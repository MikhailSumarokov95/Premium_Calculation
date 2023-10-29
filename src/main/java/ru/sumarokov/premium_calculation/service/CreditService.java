package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.Credit;
import ru.sumarokov.premium_calculation.repository.CreditRepository;

import java.util.List;

@Service
public class CreditService {

    private final CreditRepository creditRepository;
    private final CalculateService calculateService;

    @Autowired
    public CreditService(CreditRepository creditRepository, CalculateService calculateService) {
        this.creditRepository = creditRepository;
        this.calculateService = calculateService;
    }

    public List<Credit> getCredits() {
        return creditRepository.findAll();
    }

    public Credit getCredit(Long id) {
        return creditRepository.findById(id).orElseThrow();
    }

    public void saveCredit(Credit credit) {
        creditRepository.save(credit);
        calculateService.calculateEfficiency(creditRepository.findAll());
    }

    public void deleteCredit(Long id) {
        creditRepository.deleteById(id);
    }
}
