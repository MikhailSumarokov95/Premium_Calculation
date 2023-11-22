package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.PremiumLimit;
import ru.sumarokov.premium_calculation.repository.PremiumLimitRepository;

@Service
public class PremiumLimitService {

    private final PremiumLimitRepository premiumLimitRepository;

    @Autowired
    public PremiumLimitService(PremiumLimitRepository premiumLimitRepository) {
        this.premiumLimitRepository = premiumLimitRepository;
    }

    public PremiumLimit getPremiumLimit() {
        return premiumLimitRepository.findByIsActualTrue()
                .orElse(new PremiumLimit());
    }

    public void savePremiumLimit(PremiumLimit premiumLimit) {
        premiumLimit.setIsActual(true);
        premiumLimitRepository.save(premiumLimit);
    }
}
