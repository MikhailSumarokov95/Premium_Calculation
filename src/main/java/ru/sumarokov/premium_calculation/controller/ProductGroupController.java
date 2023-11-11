package ru.sumarokov.premium_calculation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/create")
    public String getProductGroupForm(Model model) {
        model.addAttribute("productGroup", new ProductGroup());
        return "admin/product_group/form";
    }

    @GetMapping("/{id}")
    public String getProductGroupForm(@PathVariable Long id, Model model) {
        model.addAttribute("productGroup", productGroupService.getProductGroup(id));
        return "admin/product_group/form";
    }

    @PostMapping("/save")
    public String saveProductGroup(@ModelAttribute ProductGroup productGroup) {
        productGroupService.saveProductGroup(productGroup);
        return "redirect:/admin";
    }

    @GetMapping("{id}/delete")
    public String deleteProductGroup(@PathVariable Long id) {
        productGroupService.deleteProductGroup(id);
        return "redirect:/admin";
    }
}
