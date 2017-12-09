package com.jarnoluu.service;

import com.jarnoluu.domain.Article;
import com.jarnoluu.domain.Category;
import com.jarnoluu.domain.Picture;
import com.jarnoluu.repository.CategoryRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestDataService {
    @Autowired
    private NewsService newsService;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    public void createTestData() throws IOException {
        this.categoryRepository.save(new Category("Kotimaa", null));
        this.categoryRepository.save(new Category("Ulkomaa", null));
        this.categoryRepository.save(new Category("Viihde", null));
        
        String title = "Haluatko miljonääriksi? -kauden finaalissa melkoinen yllätys - Jaajo ylipuhuu kilpailijan jatkamaan, vaikka tämä on luovuttamassa";
        String lead = "Lauantaina Haluatko miljonääriksi? -ohjelmassa päästään seuraamaan kauden viimeistä jaksoa, jossa Hanna Parhaniemi Kalajoelta jatkaa miljoonan tavoittelua.";
        String content = "Haluatko miljonääriksi? -ohjelman lauantain jaksossa visailua jatkaa Hanna Parhaniemi, joka pääsi jo hyvään alkuun edellisessä jaksossa. Jaksossa nähdään hyvin harvinainen tilanne, kun kilpailija on jättämässä pelin kesken, mutta päättää Jaajo Linnonmaan suostuttelun jälkeen kuitenkin jatkaa peliään.\nHanna on ottamassa rahat ja lopettamassa pelin, kun hän ei tiedä vastausta kysymykseen: Mihin Suomen kaupunkiin voi erityisesti liittää perinneruoan nimeltä lapskoussi?";
        
        Path path = Paths.get("pics/miljonaari_etu71217TK_tw.jpg");
        byte[] data = Files.readAllBytes(path);
        
        Picture pic = new Picture("miljonaari_etu71217TK_tw.jpg", "image/jpeg", data.length, data);
        
        List<Category> categories = new ArrayList();
        categories.add(this.categoryRepository.getOne(1l));
        categories.add(this.categoryRepository.getOne(3l));
        
        newsService.publishArticle(title, lead, content, pic, categories);
    }
}
