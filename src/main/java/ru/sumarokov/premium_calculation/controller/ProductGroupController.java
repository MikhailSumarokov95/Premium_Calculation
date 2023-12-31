package ru.sumarokov.premium_calculation.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sumarokov.premium_calculation.entity.ProductGroup;
import ru.sumarokov.premium_calculation.service.ProductGroupService;

@Controller
@RequestMapping("product-group")
public class ProductGroupController {

    private final ProductGroupService productGroupService;

    @Autowired
    public ProductGroupController(ProductGroupService productGroupService) {
        this.productGroupService = productGroupService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/create")
    public String getProductGroupForm(Model model) {
        model.addAttribute("productGroup", new ProductGroup());
        return "admin/product_group/form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public String getProductGroupForm(@PathVariable Long id, Model model) {
        model.addAttribute("productGroup", productGroupService.getProductGroup(id));
        return "admin/product_group/form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public String saveProductGroup(@ModelAttribute @Valid ProductGroup productGroup,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/product_group/form";
        }
        productGroupService.saveProductGroup(productGroup);
        return "redirect:/admin";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("{id}/delete")
    public String deleteProductGroup(@PathVariable Long id) {
        productGroupService.deleteProductGroup(id);
        return "redirect:/admin";
    }
}
