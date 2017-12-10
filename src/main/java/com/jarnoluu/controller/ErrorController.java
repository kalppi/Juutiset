package com.jarnoluu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("*")
    public String notFound() {
        return "forward:/error404";
    }
    
    @GetMapping("/error404")
    public String error404(Model model) {
        model.addAttribute("message", "sivua ei löydy");
        
        return "error";
    }
}
