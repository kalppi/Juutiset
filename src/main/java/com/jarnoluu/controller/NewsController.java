package com.jarnoluu.controller;

import com.jarnoluu.domain.Category;
import com.jarnoluu.repository.CategoryRepository;
import com.jarnoluu.service.NewsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewsController {
    @Autowired
    private NewsService newsService;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    private List<Category> categories() {
        return this.categoryRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
    }
    
    @GetMapping("/uutiset")
    public String index(Model model) {
        model.addAttribute("categories", this.categories());
        model.addAttribute("articles", this.newsService.getLatest());
        
        return "index";
    }
    
    @GetMapping("/uutiset/uusimmat")
    public String latest(Model model) {
        model.addAttribute("categories", this.categories());
        model.addAttribute("articles", this.newsService.getLatest());
        
        return "list";
    }
}
