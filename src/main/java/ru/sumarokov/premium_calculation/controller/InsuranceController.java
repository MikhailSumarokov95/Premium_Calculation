package ru.sumarokov.premium_calculation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sumarokov.premium_calculation.entity.Insurance;
import ru.sumarokov.premium_calculation.service.InsuranceService;

@Controller
@RequestMapping("insurance")
public class InsuranceController {

    private final InsuranceService insuranceService;

    @Autowired
    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @GetMapping("/create")
    public String getInsuranceForm(Model model) {
        model.addAttribute("insurance", new Insurance());
        return "admin/insurance/form";
    }

    @GetMapping("/{id}")
    public String getInsuranceForm(@PathVariable Long id, Model model) {
        model.addAttribute("insurance", insuranceService.getInsurance(id));
        return "admin/insurance/form";
    }

    @PostMapping("/save")
    public String saveInsurance(@ModelAttribute Insurance insurance) {
        insuranceService.saveInsurance(insurance);
        return "redirect:/admin";
    }

    @GetMapping("{id}/delete")
    public String deleteInsurance(@PathVariable Long id) {
        insuranceService.deleteInsurance(id);
        return "redirect:/admin";
    }
}
