package com.jarnoluu.juutiset.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorController {
    @GetMapping("*")
    public String notFound() {
        return "forward:/404";
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @GetMapping("/404")
    public String error404(Model model) {
        model.addAttribute("message", "sivua ei lö  ydy");
        model.addAttribute("title", "Virhe: sivua ei löydy");
        
        return "eerror";
    }
}
