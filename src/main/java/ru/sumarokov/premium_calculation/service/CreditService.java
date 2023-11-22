package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sumarokov.premium_calculation.entity.Credit;
import ru.sumarokov.premium_calculation.entity.User;
import ru.sumarokov.premium_calculation.exception.EntityNotFoundException;
import ru.sumarokov.premium_calculation.repository.CreditRepository;

import java.util.List;

@Service
public class CreditService {

    private final CreditRepository creditRepository;
    private final EfficiencyService efficiencyService;

    @Autowired
    public CreditService(CreditRepository creditRepository,
                         EfficiencyService efficiencyService) {
        this.creditRepository = creditRepository;
        this.efficiencyService = efficiencyService;
    }

    public List<Credit> getCredits(User user) {
        efficiencyService.calculateEfficiency(user);
        return creditRepository.findByUserIdOrderByIdAsc(user.getId());
    }

    public Credit getCredit(Long id, User user) {
        return creditRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void saveCredit(Credit credit, User user) {
        if (credit.getId() != null
                && !creditRepository.existsByIdAndUserId(user.getId(), user.getId())) {
            throw new EntityNotFoundException();
        }
        creditRepository.save(credit);
        efficiencyService.calculateEfficiency(user);
    }

    @Transactional
    public void deleteCredit(Long id, User user) {
        if (!creditRepository.existsByIdAndUserId(id, user.getId())) {
            throw new EntityNotFoundException();
        }
        creditRepository.deleteById(id);
        efficiencyService.calculateEfficiency(user);
    }
}
