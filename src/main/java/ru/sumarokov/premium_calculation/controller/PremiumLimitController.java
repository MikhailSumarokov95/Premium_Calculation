package ru.sumarokov.premium_calculation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sumarokov.premium_calculation.entity.PremiumLimit;
import ru.sumarokov.premium_calculation.service.PremiumLimitService;

@Controller
@RequestMapping("premium-limit")
public class PremiumLimitController {

    private final PremiumLimitService premiumLimitService;

    @Autowired
    public PremiumLimitController(PremiumLimitService premiumLimitService) {
        this.premiumLimitService = premiumLimitService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public String getPremiumLimitForm(@PathVariable Long id, Model model) {
        model.addAttribute("premiumLimit", premiumLimitService.getPremiumLimit());
        return "admin/premium_limit/form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public String savePremiumLimit(@ModelAttribute PremiumLimit premiumLimit) {
        premiumLimitService.savePremiumLimit(premiumLimit);
        return "redirect:/admin";
    }
}
