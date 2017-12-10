package com.jarnoluu.juutiset.controller;

import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ErrorController {
    @GetMapping("*")
    public String notFound() {
        return "forward:/error404";
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @GetMapping("/error404")
    public String error404(Model model) {
        model.addAttribute("message", "sivua ei löydy");
        model.addAttribute("title", "Virhe: sivua ei löydy");
        
        return "error";
    }
}
