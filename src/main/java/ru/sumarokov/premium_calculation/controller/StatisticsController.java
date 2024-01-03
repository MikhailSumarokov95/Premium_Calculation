package ru.sumarokov.premium_calculation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sumarokov.premium_calculation.service.*;

@Controller
@RequestMapping("admin/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public String getStatistics(Model model) {
        model.addAttribute("sumAmountCredits", statisticsService.getSumAmountCredits());
        model.addAttribute("countCreditSpecialist", statisticsService.getCountCreditSpecialist());
        model.addAttribute("sumTotalPremiumSpecialists", statisticsService.getSumTotalPremiumSpecialists());
        model.addAttribute("totalVolumeCreditsForFurs", statisticsService.getTotalVolumeCreditsForFurs());
        model.addAttribute("totalInsuredVolumeCredits", statisticsService.getTotalInsuredVolumeCredits());
        model.addAttribute("averageInsurancePenetrationPercentage", statisticsService.getAverageInsurancePenetrationPercentage());
        return "admin/statistics";
    }
}
