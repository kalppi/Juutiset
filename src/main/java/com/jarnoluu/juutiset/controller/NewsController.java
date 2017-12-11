package com.jarnoluu.juutiset.controller;

import com.jarnoluu.juutiset.domain.Article;
import com.jarnoluu.juutiset.domain.Category;
import com.jarnoluu.juutiset.repository.ArticleRepository;
import com.jarnoluu.juutiset.repository.CategoryRepository;
import com.jarnoluu.juutiset.service.NewsService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NewsController {
    @Autowired
    private NewsService newsService;
    
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @ModelAttribute("categories")
    private List<Category> categories() {
        return this.categoryRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
    }
    
    @GetMapping("/uutiset")
    public String index(Model model) {
        model.addAttribute("articles", this.newsService.getLatest(5));
        model.addAttribute("title", "Etusivu");
        
        return "index";
    }
    
    @GetMapping({"/uutiset/uusimmat", "/uutiset/uusimmat/{page}"})
    public String latest(Model model, @PathVariable Map<String, String> variables) {
        final int limit = 10;
        int page = 1;
        int max = (int)Math.ceil(this.articleRepository.count() / (float)limit);
        
        if(variables.containsKey("page")) {
            page = Integer.parseInt(variables.get("page"));
            
            if(page < 1) {
                return "redirect:/uutiset/uusimmat";
            } else if(page > max) {
                return "redirect:/uutiset/uusimmat/" + (max);
            }
        }
        
        model.addAttribute("articles", this.newsService.getLatestPage(page, limit));
        model.addAttribute("title", "Uusimmat");
        model.addAttribute("page", page);
        model.addAttribute("pages", max);
        model.addAttribute("link", "uusimmat");
        
        return "list";
    }
    
    @GetMapping("/uutiset/suosituimmat")
    public String popular(Model model) {
        model.addAttribute("articles", this.newsService.getPopular());
        model.addAttribute("title", "Suosituimmat");
        
        return "list";
    }
    
    @GetMapping("/uutiset/alue/{id}")
    public String category(Model model, @PathVariable long id) {
        Optional<Category> cat = this.categoryRepository.findById(id);
        
        if(!cat.isPresent()) {
            return "forward:/404";
        }
        
        Category category = cat.get();
        
        model.addAttribute("articles", category.getArticles());
        model.addAttribute("title", "Alue: " + category.getName());
        
        return "list";
    }
    
    @GetMapping("/uutinen/{id}")
    public String view(Model model, @PathVariable long id) {
        Optional<Article> oArticle = this.articleRepository.findById(id);
        
        if(!oArticle.isPresent()) {
            return "forward:/404";
        }
        
        Article article = oArticle.get();
        
        this.newsService.increaseViews(article);
        
        model.addAttribute("article", article);
        model.addAttribute("title", "Uutinen: " + article.getTitle());
        
        return "article";
    }
}
