package ru.sumarokov.premium_calculation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.sumarokov.premium_calculation.entity.Credit;
import ru.sumarokov.premium_calculation.service.CreditService;

@Controller
@RequestMapping("credit")
public class CreditController {

    private final CreditService creditService;

    @Autowired
    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @GetMapping()
    public String getCreditList(Model model) {
        model.addAttribute("credits", creditService.getCredits());
        return "credit/list";
    }

    @GetMapping("/create")
    public String getCreditForm(Model model) {
        model.addAttribute("credit", new Credit());
        return "credit/form";
    }

    @GetMapping("/{id}")
    public String getCreditForm(@PathVariable Long id, Model model) {
        model.addAttribute("credit", creditService.getCredit(id));
        return "credit/form";
    }

    @PostMapping("/save")
    public String saveCredit(@ModelAttribute Credit credit) {
        creditService.saveCredit(credit);
        return "redirect:credit";
    }

    @GetMapping("{id}/delete")
    public String deleteCredit(@ModelAttribute Long id) {
        creditService.deleteCredit(id);
        return "redirect:credit";
    }
}
