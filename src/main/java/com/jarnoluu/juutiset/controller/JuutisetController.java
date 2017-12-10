package com.jarnoluu.juutiset.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.jarnoluu.juutiset.service.TestDataService;
import java.io.IOException;
import javax.annotation.PostConstruct;

@Controller
public class JuutisetController {    
    @Autowired
    private TestDataService testDataService;
    
    @GetMapping("/")
    public String index() {
        return "redirect:/uutiset";
    }
    
    @PostConstruct
    public void addTestData() throws IOException {
        this.testDataService.createTestData();
    }
}
