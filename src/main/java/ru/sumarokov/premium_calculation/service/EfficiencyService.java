package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.Credit;
import ru.sumarokov.premium_calculation.entity.Efficiency;
import ru.sumarokov.premium_calculation.repository.PreliminaryCreditResultRepository;

import java.util.List;

@Service
public class EfficiencyService {

    private final PreliminaryCreditResultService preliminaryCreditResultService;
    private final PreliminaryCreditResultRepository preliminaryCreditResultRepository;

    @Autowired
    public EfficiencyService(PreliminaryCreditResultService preliminaryCreditResultService,
                             PreliminaryCreditResultRepository preliminaryCreditResultRepository) {
        this.preliminaryCreditResultService = preliminaryCreditResultService;
        this.preliminaryCreditResultRepository = preliminaryCreditResultRepository;
    }

    public Efficiency calculateEfficiency(List<Credit> credits) {
        Efficiency efficiency = new Efficiency();

        preliminaryCreditResultService.calculatePreliminaryCreditResults();
        efficiency.setPremiumForCredits(preliminaryCreditResultRepository.getSumCreditTotal().orElseThrow());

        return efficiency;
    }
}
