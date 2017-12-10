package com.jarnoluu.juutiset.controller;

import com.jarnoluu.juutiset.domain.Article;
import com.jarnoluu.juutiset.domain.Category;
import com.jarnoluu.juutiset.domain.Picture;
import com.jarnoluu.juutiset.repository.ArticleRepository;
import com.jarnoluu.juutiset.repository.PictureRepository;
import com.jarnoluu.juutiset.service.LoginService;
import com.jarnoluu.juutiset.service.NewsService;
import com.jarnoluu.juutiset.service.PictureService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private NewsService newsService;
    
    @Autowired
    private LoginService loginService;
    
    @Autowired
    private PictureService pictureService;
    
    @Autowired
    private PictureRepository pictureRepository;
    
    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("title", "admin");
        model.addAttribute("articles", this.articleRepository.findAll());
        
        return "admin";
    }
    
    @PostMapping("/uutinen/kirjoita")
    public String create(RedirectAttributes re, @RequestParam("title") String title, @RequestParam("picture") MultipartFile file, @RequestParam("pictureId") Long pictureId, @RequestParam("lead") String lead, @RequestParam("content") String content) throws IOException {
        title = title.trim();
        lead = lead.replaceAll("[\r\n]+", "\n").trim();
        content = content.replaceAll("[\r\n]+", "\n").trim();
        
        List<String> errors = new ArrayList();
        
        if(title.length() <= 10) errors.add("Otsikon pitää olla yli 10 merkkiä");
        if(lead.length() <= 50) errors.add("Ingressin pitää olla yli 50 merkkiä");
        if(content.length() <= 100) errors.add("Tekstin pitää olla yli 100 merkkiä");
        if(file.isEmpty() && pictureId == null) errors.add("Ei kuvaa");
        
        Picture picture = null;
        
        if(!file.isEmpty()) {
            picture = new Picture(file.getName(), file.getContentType(), file.getSize(), file.getBytes());
        } else {
            if(pictureId != null) {
                picture = this.pictureRepository.getOne(pictureId);
            }
        }
        
        if(errors.size() > 0) {
            re.addFlashAttribute("ctitle", title);
            re.addFlashAttribute("clead", lead);
            re.addFlashAttribute("ccontent", content);
            re.addFlashAttribute("errors", errors);
            
            if(picture != null) {
                Picture small = this.pictureService.createSmaller(picture);
                Picture thumb = this.pictureService.createThumb(picture);
                
                re.addFlashAttribute("cpicture", small.getId());
                re.addFlashAttribute("ctpicture", thumb.getId());
            }
            
            return "redirect:/uutinen/kirjoita";
        }
        
        List<Category> categories = new ArrayList();
        
        Article article = this.newsService.publishArticle(title, lead, content, picture, categories);
        
        return "redirect:/uutinen/" + article.getId();
    }
    
    @GetMapping("/uutinen/kirjoita")
    public String viewCreate(Model model) {
        model.addAttribute("title", "Uusi uutinen");
        
        return "create";
    }
    
    @GetMapping("/uutinen/{id}/muokkaa")
    public String viewEdit(Model model, @PathVariable long id) {
        Optional<Article> article = this.articleRepository.findById(id);
        
        if(!article.isPresent()) {
            return "forward:/error404";
        }
        
        model.addAttribute("article", article.get());
        model.addAttribute("title", "Muokkaa");
        
        return "edit";
    }
    
    @PostMapping("/uutinen/{id}/muokkaa")
    public String edit(RedirectAttributes re, @PathVariable long id, @RequestParam String title, @RequestParam String lead, @RequestParam String content) throws IOException {
        Optional<Article> oArticle = this.articleRepository.findById(id);
        
        if(!oArticle.isPresent()) {
            return "forward:/error404";
        }
        
        title = title.trim();
        lead = lead.replaceAll("[\r\n]+", "\n").trim();
        content = content.replaceAll("[\r\n]+", "\n").trim();
        
        List<String> errors = new ArrayList();
        
        if(title.length() <= 10) errors.add("Otsikon pitää olla yli 10 merkkiä");
        if(lead.length() <= 50) errors.add("Ingressin pitää olla yli 50 merkkiä");
        if(content.length() <= 100) errors.add("Tekstin pitää olla yli 100 merkkiä");
        
        if(errors.size() > 0) {
            re.addFlashAttribute("ctitle", title);
            re.addFlashAttribute("clead", lead);
            re.addFlashAttribute("ccontent", content);
            re.addFlashAttribute("errors", errors);
            
            return "redirect:/uutinen/" + id + "/muokkaa";
        }
        
        this.newsService.editArticle(oArticle.get(), title, lead, content);
        
        return "redirect:/admin";
    }
}
