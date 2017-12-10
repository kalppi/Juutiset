package com.jarnoluu.controller;

import javax.servlet.http.HttpServletResponse;
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
    public String error404(Model model, HttpServletResponse response) {
        model.addAttribute("message", "sivua ei löydy");
        model.addAttribute("title", "Virhe: sivua ei löydy");
        
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        
        return "error";
    }
}
