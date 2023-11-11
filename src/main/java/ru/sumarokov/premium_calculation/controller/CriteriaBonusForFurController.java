package ru.sumarokov.premium_calculation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sumarokov.premium_calculation.entity.CriteriaBonusForFur;
import ru.sumarokov.premium_calculation.service.CriteriaBonusForFurService;

@Controller
@RequestMapping("criteria-bonus-for-fur")
public class CriteriaBonusForFurController {

    private final CriteriaBonusForFurService criteriaBonusForFurService;

    @Autowired
    public CriteriaBonusForFurController(CriteriaBonusForFurService criteriaBonusForFurService) {
        this.criteriaBonusForFurService = criteriaBonusForFurService;
    }

    @GetMapping("/create")
    public String getCriteriaBonusForFurForm(Model model) {
        model.addAttribute("criteria", new CriteriaBonusForFur());
        return "admin/criteria_bonus_for_fur/form";
    }

    @GetMapping("/{id}")
    public String getCriteriaBonusForFurForm(@PathVariable Long id, Model model) {
        model.addAttribute("criteria", criteriaBonusForFurService.getCriteriaBonusForFur(id));
        return "admin/criteria_bonus_for_fur/form";
    }

    @PostMapping("/save")
    public String saveCriteriaBonusForFur(@ModelAttribute CriteriaBonusForFur criteriaBonusForFur) {
        criteriaBonusForFurService.saveCriteriaBonusForFur(criteriaBonusForFur);
        return "redirect:/admin";
    }

    @GetMapping("{id}/delete")
    public String deleteCriteriaBonusForFur(@PathVariable Long id) {
        criteriaBonusForFurService.deleteCriteriaBonusForFur(id);
        return "redirect:/admin";
    }
}
