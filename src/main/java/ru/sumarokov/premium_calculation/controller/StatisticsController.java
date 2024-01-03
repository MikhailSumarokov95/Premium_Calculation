package ru.sumarokov.premium_calculation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sumarokov.premium_calculation.service.CreditService;
import ru.sumarokov.premium_calculation.service.UserService;

@Controller
@RequestMapping("admin/statistics")
public class StatisticsController {

    private final CreditService creditService;
    private final UserService userService;

    @Autowired
    public StatisticsController(CreditService creditService,
                                UserService userService) {
        this.creditService = creditService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public String getStatistics(Model model) {
        model.addAttribute("sumAmountCredits", creditService.getSumAmountCredits());
        model.addAttribute("countCreditSpecialist", userService.getCountCreditSpecialist());
        return "admin/statistics";
    }
}
