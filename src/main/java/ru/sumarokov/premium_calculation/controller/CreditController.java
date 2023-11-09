package ru.sumarokov.premium_calculation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.sumarokov.premium_calculation.entity.Credit;
import ru.sumarokov.premium_calculation.entity.Insurance;
import ru.sumarokov.premium_calculation.entity.ProductGroup;
import ru.sumarokov.premium_calculation.repository.InsuranceRepository;
import ru.sumarokov.premium_calculation.repository.PremiumLimitRepository;
import ru.sumarokov.premium_calculation.repository.ProductGroupRepository;
import ru.sumarokov.premium_calculation.service.*;

@Controller
@RequestMapping("credit")
public class CreditController {

    private final CreditService creditService;
    private final ProductGroupService productGroupService;
    private final InsuranceService insuranceService;
    private final EfficiencyService efficiencyService;
    private final PreliminaryCreditResultService preliminaryCreditResultService;

    @Autowired
    public CreditController(CreditService creditService,
                            ProductGroupService productGroupService,
                            InsuranceService insuranceService,
                            EfficiencyService efficiencyService,
                            PreliminaryCreditResultService preliminaryCreditResultService) {
        this.creditService = creditService;
        this.productGroupService = productGroupService;
        this.insuranceService = insuranceService;
        this.efficiencyService = efficiencyService;
        this.preliminaryCreditResultService = preliminaryCreditResultService;
    }

    @GetMapping()
    public String getCreditList(Model model) {
        model.addAttribute("credits", creditService.getCredits());
        model.addAttribute("efficiency", efficiencyService.getEfficiency());
        model.addAttribute("preliminaryCreditResults", preliminaryCreditResultService.getPreliminaryCreditResults());
        return "credit/list";
    }

    @GetMapping("/create")
    public String getCreditForm(Model model) {
        model.addAttribute("credit", new Credit());
        model.addAttribute("productGroups", productGroupService.getProductGroup());
        model.addAttribute("insurances", insuranceService.getInsurance());
        return "credit/form";
    }

    @GetMapping("/{id}")
    public String getCreditForm(@PathVariable Long id, Model model) {
        model.addAttribute("credit", creditService.getCredit(id));
        model.addAttribute("productGroups",  productGroupService.getProductGroup());
        model.addAttribute("insurances", insuranceService.getInsurance());
        return "credit/form";
    }

    @PostMapping("/save")
    public String saveCredit(@ModelAttribute Credit credit) {
        creditService.saveCredit(credit);
        return "redirect:/credit";
    }

    @GetMapping("{id}/delete")
    public String deleteCredit(@PathVariable Long id) {
        creditService.deleteCredit(id);
        return "redirect:/credit";
    }
}
