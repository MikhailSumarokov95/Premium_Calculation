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

    private final CreditService creditService;
    private final UserService userService;
    private final EfficiencyService efficiencyService;
    private final FurResultService furResultService;
    private final InsuranceResultService insuranceResultService;

    @Autowired
    public StatisticsController(CreditService creditService,
                                UserService userService,
                                EfficiencyService efficiencyService,
                                FurResultService furResultService,
                                InsuranceResultService insuranceResultService) {
        this.creditService = creditService;
        this.userService = userService;
        this.efficiencyService = efficiencyService;
        this.furResultService = furResultService;
        this.insuranceResultService = insuranceResultService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public String getStatistics(Model model) {
        model.addAttribute("sumAmountCredits", creditService.getSumAmountCredits());
        model.addAttribute("countCreditSpecialist", userService.getCountCreditSpecialist());
        model.addAttribute("sumTotalPremiumSpecialists", efficiencyService.getSumTotalPremiumSpecialists());
        model.addAttribute("sumAmountAllCreditsCategoryFur", furResultService.getSumAmountAllCreditsCategoryFur());
        model.addAttribute("sumAllInsuranceVolume", insuranceResultService.getSumAllInsuranceVolume());
        model.addAttribute("averageInsurancePenetrationPercentage", insuranceResultService.getAverageInsurancePenetrationPercentage());
        return "admin/statistics";
    }
}
