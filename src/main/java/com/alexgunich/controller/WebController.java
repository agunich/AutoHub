package com.alexgunich.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping("/")
    public String index() {
        return "index.html"; // Это файл будет обслуживаться как главная страница
    }
}
