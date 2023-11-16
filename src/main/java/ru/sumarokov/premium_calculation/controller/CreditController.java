package ru.sumarokov.premium_calculation.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sumarokov.premium_calculation.entity.Credit;
import ru.sumarokov.premium_calculation.service.*;

@Controller
@RequestMapping("credit")
public class CreditController {

    private final CreditService creditService;
    private final ProductGroupService productGroupService;
    private final InsuranceService insuranceService;
    private final EfficiencyService efficiencyService;
    private final PreliminaryCreditResultService preliminaryCreditResultService;
    private final AuthService authService;

    @Autowired
    public CreditController(CreditService creditService,
                            ProductGroupService productGroupService,
                            InsuranceService insuranceService,
                            EfficiencyService efficiencyService,
                            PreliminaryCreditResultService preliminaryCreditResultService,
                            AuthService authService) {
        this.creditService = creditService;
        this.productGroupService = productGroupService;
        this.insuranceService = insuranceService;
        this.efficiencyService = efficiencyService;
        this.preliminaryCreditResultService = preliminaryCreditResultService;
        this.authService = authService;
    }

    @PreAuthorize("hasRole('ROLE_CREDIT_SPECIALIST')")
    @GetMapping()
    public String getCreditList(Model model) {
        model.addAttribute("credits", creditService.getCredits());
        model.addAttribute("efficiency", efficiencyService.getEfficiency());
        model.addAttribute("preliminaryCreditResults", preliminaryCreditResultService.getPreliminaryCreditResults());
        return "credit/list";
    }

    @PreAuthorize("hasRole('ROLE_CREDIT_SPECIALIST')")
    @GetMapping("/create")
    public String getCreditForm(Model model) {
        model.addAttribute("credit", new Credit(authService.getUser()));
        model.addAttribute("productGroups", productGroupService.getProductGroups());
        model.addAttribute("insurances", insuranceService.getInsurances());
        return "credit/form";
    }

    @PreAuthorize("hasRole('ROLE_CREDIT_SPECIALIST')")
    @GetMapping("/{id}")
    public String getCreditForm(@PathVariable Long id, Model model) {
        model.addAttribute("credit", creditService.getCredit(id));
        model.addAttribute("productGroups", productGroupService.getProductGroups());
        model.addAttribute("insurances", insuranceService.getInsurances());
        return "credit/form";
    }

    @PreAuthorize("hasRole('ROLE_CREDIT_SPECIALIST')")
    @PostMapping("/save")
    public String saveCredit(@ModelAttribute @Valid Credit credit,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("productGroups", productGroupService.getProductGroups());
            model.addAttribute("insurances", insuranceService.getInsurances());
            return "credit/form";
        }
        creditService.saveCredit(credit);
        return "redirect:/credit";
    }

    @PreAuthorize("hasRole('ROLE_CREDIT_SPECIALIST')")
    @GetMapping("{id}/delete")
    public String deleteCredit(@PathVariable Long id) {
        creditService.deleteCredit(id);
        return "redirect:/credit";
    }
}
