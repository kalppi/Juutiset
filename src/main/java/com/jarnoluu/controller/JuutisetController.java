package com.jarnoluu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.jarnoluu.domain.Article;
import com.jarnoluu.domain.Picture;
import com.jarnoluu.repository.ArticleRepository;
import com.jarnoluu.service.NewsService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.annotation.PostConstruct;

@Controller
public class JuutisetController {
    @Autowired
    private NewsService newsService;
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("articles", this.newsService.getLatest());
        
        return "index";
    }
    
    @PostConstruct
    public void addTestData() throws IOException {
        String title = "Haluatko miljonääriksi? -kauden finaalissa melkoinen yllätys - Jaajo ylipuhuu kilpailijan jatkamaan, vaikka tämä on luovuttamassa";
        String lead = "Lauantaina Haluatko miljonääriksi? -ohjelmassa päästään seuraamaan kauden viimeistä jaksoa, jossa Hanna Parhaniemi Kalajoelta jatkaa miljoonan tavoittelua.";
        String content = "Haluatko miljonääriksi? -ohjelman lauantain jaksossa visailua jatkaa Hanna Parhaniemi, joka pääsi jo hyvään alkuun edellisessä jaksossa. Jaksossa nähdään hyvin harvinainen tilanne, kun kilpailija on jättämässä pelin kesken, mutta päättää Jaajo Linnonmaan suostuttelun jälkeen kuitenkin jatkaa peliään.\nHanna on ottamassa rahat ja lopettamassa pelin, kun hän ei tiedä vastausta kysymykseen: Mihin Suomen kaupunkiin voi erityisesti liittää perinneruoan nimeltä lapskoussi?";
        
        Path path = Paths.get("pics/miljonaari_etu71217TK_tw.jpg");
        byte[] data = Files.readAllBytes(path);
        
        Picture pic = new Picture("miljonaari_etu71217TK_tw.jpg", "image/jpeg", data.length, data);
        
        Article article = newsService.publishArticle(title, lead, content, pic);
        
        System.out.println(article);
    }
}
