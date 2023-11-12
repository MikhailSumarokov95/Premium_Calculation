package ru.sumarokov.premium_calculation.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "redirect:/credit";
    }
}
