package ru.sumarokov.premium_calculation.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sumarokov.premium_calculation.helper.Role;

@Controller
public class IndexController {

    @RequestMapping("")
    public String index(HttpServletRequest request) {
        if (request.isUserInRole(Role.ROLE_ADMIN.name())) {
            return "redirect:/admin";
        } else {
            return "redirect:/credit";
        }
    }
}
