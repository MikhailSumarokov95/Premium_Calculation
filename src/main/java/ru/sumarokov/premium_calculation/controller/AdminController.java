package ru.sumarokov.premium_calculation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sumarokov.premium_calculation.helper.Role;
import ru.sumarokov.premium_calculation.service.*;

@Controller
@RequestMapping("admin")
public class AdminController {

    private final CriteriaBonusForFurService criteriaBonusForFurService;
    private final InsuranceService insuranceService;
    private final PremiumLimitService premiumLimitService;
    private final ProductGroupService productGroupService;
    private final ProductivityLevelService productivityLevelService;

    @Autowired
    public AdminController(CriteriaBonusForFurService criteriaBonusForFurService,
                           InsuranceService insuranceService,
                           PremiumLimitService premiumLimitService,
                           ProductGroupService productGroupService,
                           ProductivityLevelService productivityLevelService) {
        this.criteriaBonusForFurService = criteriaBonusForFurService;
        this.insuranceService = insuranceService;
        this.premiumLimitService = premiumLimitService;
        this.productGroupService = productGroupService;
        this.productivityLevelService = productivityLevelService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping()
    public String getHome(Model model) {
        model.addAttribute("criteriasFur", criteriaBonusForFurService.getCriteriasBonusForFur());
        model.addAttribute("insurances", insuranceService.getInsurances());
        model.addAttribute("premiumLimit", premiumLimitService.getPremiumLimit());
        model.addAttribute("productGroup", productGroupService.getProductGroups());
        model.addAttribute("productivityLevels", productivityLevelService.getProductivityLevels());
        return "admin/home";
    }
}
