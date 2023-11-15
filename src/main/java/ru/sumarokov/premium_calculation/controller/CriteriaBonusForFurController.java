package ru.sumarokov.premium_calculation.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/create")
    public String getCriteriaBonusForFurForm(Model model) {
        model.addAttribute("criteriaBonusForFur", new CriteriaBonusForFur());
        return "admin/criteria_bonus_for_fur/form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public String getCriteriaBonusForFurForm(@PathVariable Long id, Model model) {
        model.addAttribute("criteriaBonusForFur", criteriaBonusForFurService.getCriteriaBonusForFur(id));
        return "admin/criteria_bonus_for_fur/form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public String saveCriteriaBonusForFur(@ModelAttribute @Valid CriteriaBonusForFur criteriaBonusForFur,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/criteria_bonus_for_fur/form";
        }
        criteriaBonusForFurService.saveCriteriaBonusForFur(criteriaBonusForFur);
        return "redirect:/admin";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("{id}/delete")
    public String deleteCriteriaBonusForFur(@PathVariable Long id) {
        criteriaBonusForFurService.deleteCriteriaBonusForFur(id);
        return "redirect:/admin";
    }
}
