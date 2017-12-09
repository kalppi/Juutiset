package com.jarnoluu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("*")
    public String notFound() {
        return "error";
    }
}
