package ru.sumarokov.premium_calculation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sumarokov.premium_calculation.entity.ProductivityLevel;
import ru.sumarokov.premium_calculation.service.ProductivityLevelService;

@Controller
@RequestMapping("productivity-level")
public class ProductivityLevelController {

    private final ProductivityLevelService productivityLevelService;

    @Autowired
    public ProductivityLevelController(ProductivityLevelService productivityLevelService) {
        this.productivityLevelService = productivityLevelService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/create")
    public String getProductivityLevelForm(Model model) {
        model.addAttribute("productivityLevel", new ProductivityLevel());
        return "admin/productivity_level/form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public String getProductivityLevelForm(@PathVariable Long id, Model model) {
        model.addAttribute("productivityLevel", productivityLevelService.getProductivityLevel(id));
        return "admin/productivity_level/form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public String saveProductivityLevel(@ModelAttribute ProductivityLevel productivityLevel) {
        productivityLevelService.saveProductivityLevel(productivityLevel);
        return "redirect:/admin";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("{id}/delete")
    public String deleteProductGroup(@PathVariable Long id) {
        productivityLevelService.deleteProductivityLevel(id);
        return "redirect:/admin";
    }
}
