package com.jarnoluu.controller;

import com.jarnoluu.domain.Category;
import com.jarnoluu.repository.CategoryRepository;
import com.jarnoluu.service.NewsService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        model.addAttribute("title", "Etusivu");
        
        return "index";
    }
    
    @GetMapping("/uutiset/uusimmat")
    public String latest(Model model) {
        model.addAttribute("categories", this.categories());
        model.addAttribute("articles", this.newsService.getLatest());
        model.addAttribute("title", "Uusimmat");
        
        return "list";
    }
    
    @GetMapping("/uutiset/suosituimmat")
    public String popular(Model model) {
        model.addAttribute("categories", this.categories());
        model.addAttribute("articles", this.newsService.getLatest());
        model.addAttribute("title", "Suosituimmat");
        
        return "list";
    }
    
    @GetMapping("/alue/{id}")
    public String category(Model model, @PathVariable long id) {
        Optional<Category> cat = this.categoryRepository.findById(id);
        
        if(!cat.isPresent()) {
            return "redirect:/error";
        }
        
        Category category = cat.get();
        
        model.addAttribute("categories", this.categories());
        model.addAttribute("articles", category.getArticles());
        model.addAttribute("title", "Alue: " + category.getName());
        
        return "list";
    }
}
