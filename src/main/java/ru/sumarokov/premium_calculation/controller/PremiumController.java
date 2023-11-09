package ru.sumarokov.premium_calculation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sumarokov.premium_calculation.service.*;

@Controller
@RequestMapping("premium")
public class PremiumController {

    private final EfficiencyService efficiencyService;
    private final FurResultService furResultService;
    private final InsuranceResultService insuranceResultService;
    private final ProductivityResultService productivityResultService;

    @Autowired
    public PremiumController(EfficiencyService efficiencyService,
                             FurResultService furResultService,
                             InsuranceResultService insuranceResultService,
                             ProductivityResultService productivityResultService) {
        this.efficiencyService = efficiencyService;
        this.furResultService = furResultService;
        this.insuranceResultService = insuranceResultService;
        this.productivityResultService = productivityResultService;
    }

    @GetMapping()
    public String getCreditList(Model model) {
        model.addAttribute("efficiency", efficiencyService.getEfficiency());
        model.addAttribute("furResult", furResultService.getFurResult());
        model.addAttribute("insuranceResult", insuranceResultService.getInsuranceResult());
        model.addAttribute("productivityResult", productivityResultService.getProductivityResult());
        return "credit/premium";
    }
}
