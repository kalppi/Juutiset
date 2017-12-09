package com.jarnoluu.controller;

import com.jarnoluu.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.jarnoluu.service.NewsService;
import com.jarnoluu.service.TestDataService;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.data.domain.Sort;

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
