package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sumarokov.premium_calculation.entity.Credit;
import ru.sumarokov.premium_calculation.exception.EntityNotFoundException;
import ru.sumarokov.premium_calculation.repository.CreditRepository;

import java.util.List;

@Service
public class CreditService {

    private final CreditRepository creditRepository;
    private final EfficiencyService efficiencyService;
    private final AuthService authService;

    @Autowired
    public CreditService(CreditRepository creditRepository,
                         EfficiencyService efficiencyService,
                         AuthService authService) {
        this.creditRepository = creditRepository;
        this.efficiencyService = efficiencyService;
        this.authService = authService;
    }

    public List<Credit> getCredits() {
        return creditRepository.findByUserIdOrderByIdAsc(authService.getUser().getId());
    }

    public Credit getCredit(Long id) {
        return creditRepository.findByIdAndUserId(id, authService.getUser().getId())
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void saveCredit(Credit credit) {
        if (credit.getId() != null) getCredit(credit.getId());
        creditRepository.save(credit);
        efficiencyService.calculateEfficiency();
    }

    @Transactional
    public void deleteCredit(Long id) {
        getCredit(id);
        creditRepository.deleteById(id);
        efficiencyService.calculateEfficiency();
    }
}
