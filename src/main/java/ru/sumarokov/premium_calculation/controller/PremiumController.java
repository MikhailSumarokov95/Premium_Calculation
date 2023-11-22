package ru.sumarokov.premium_calculation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sumarokov.premium_calculation.entity.User;
import ru.sumarokov.premium_calculation.service.*;

@Controller
@RequestMapping("premium")
public class PremiumController {

    private final EfficiencyService efficiencyService;
    private final FurResultService furResultService;
    private final InsuranceResultService insuranceResultService;
    private final ProductivityResultService productivityResultService;
    private final AuthService authService;

    @Autowired
    public PremiumController(EfficiencyService efficiencyService,
                             FurResultService furResultService,
                             InsuranceResultService insuranceResultService,
                             ProductivityResultService productivityResultService,
                             AuthService authService) {
        this.efficiencyService = efficiencyService;
        this.furResultService = furResultService;
        this.insuranceResultService = insuranceResultService;
        this.productivityResultService = productivityResultService;
        this.authService = authService;
    }

    @PreAuthorize("hasRole('ROLE_CREDIT_SPECIALIST')")
    @GetMapping()
    public String getCreditList(Model model) {
        User user = authService.getUser();
        model.addAttribute("efficiency", efficiencyService.getEfficiency(user));
        model.addAttribute("furResult", furResultService.getFurResult(user));
        model.addAttribute("insuranceResult", insuranceResultService.getInsuranceResult(user));
        model.addAttribute("productivityResult", productivityResultService.getProductivityResult(user));
        return "credit/premium";
    }
}
