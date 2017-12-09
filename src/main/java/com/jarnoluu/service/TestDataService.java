package com.jarnoluu.service;

import com.jarnoluu.domain.Article;
import com.jarnoluu.domain.Category;
import com.jarnoluu.domain.Picture;
import com.jarnoluu.repository.ArticleRepository;
import com.jarnoluu.repository.CategoryRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestDataService {
    @Autowired
    private NewsService newsService;
    
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    private void create(String title, String lead, String content, String picture, int[] categories, LocalDateTime time, int views) throws IOException {
        Path path = Paths.get("pics/" + picture);
        byte[] data = Files.readAllBytes(path);
        
        Picture pic = new Picture(picture, "image/jpeg", data.length, data);
        
        List<Category> cats = new ArrayList();
        for(int cat : categories) {
            cats.add(this.categoryRepository.getOne((long)cat));
        }
        
        Article article = newsService.publishArticle(title, lead, content, pic, cats);
        
        article.setPublished(time);
        article.setViews(views);
        
        this.articleRepository.save(article);
    }
    
    public void createTestData() throws IOException {
        this.categoryRepository.save(new Category("Kotimaa", null));
        this.categoryRepository.save(new Category("Ulkomaa", null));
        this.categoryRepository.save(new Category("Viihde", null));
        
        List<String> titles = Arrays.asList(
                "Haluatko miljonääriksi? -kauden finaalissa melkoinen yllätys - Jaajo ylipuhuu kilpailijan jatkamaan, vaikka tämä on luovuttamassa",
                "”Olkaa kilttejä, älkää ampuko minua” - 26-vuotias itkua pidätellyt mies kuoli poliisin luoteihin ryömiessään hotellihuoneen käytävällä USA:ssa: video näyttää kaiken"
        );
                
        List<String> leads = Arrays.asList(
                "Lauantaina Haluatko miljonääriksi? -ohjelmassa päästään seuraamaan kauden viimeistä jaksoa, jossa Hanna Parhaniemi Kalajoelta jatkaa miljoonan tavoittelua.",
                "Yksi ajattelematon kädenliike koitui henkensä puolesta anoneen kahden lapsen isän kohtaloksi Mesassa Arizonassa, yhdysvaltalaismedia kertoo."
        );
        
        List<String> contents = Arrays.asList(
            "Haluatko miljonääriksi? -ohjelman lauantain jaksossa visailua jatkaa Hanna Parhaniemi, joka pääsi jo hyvään alkuun edellisessä jaksossa. Jaksossa nähdään hyvin harvinainen tilanne, kun kilpailija on jättämässä pelin kesken, mutta päättää Jaajo Linnonmaan suostuttelun jälkeen kuitenkin jatkaa peliään.\nHanna on ottamassa rahat ja lopettamassa pelin, kun hän ei tiedä vastausta kysymykseen: Mihin Suomen kaupunkiin voi erityisesti liittää perinneruoan nimeltä lapskoussi?",
            "Poliisin tapposyyte hylättiin tuomioistuimessa Arizonassa torstaina. Oikeudenkäynnissä oli kyse tilanteesta, jossa Philip Brailsford ampui aseistamattoman Daniel Shaverin hotellihuoneen käytävälle tammikuussa 2016.\nTexasilainen Shaver, 26, oli Mesan kaupungissa työmatkalla ja oli kohtalokkaana iltana kutsunut kaksi tuttavaansa hotellihuoneeseensa, jossa he olivat nauttineet alkoholia. Tuholaistorjujana työskennellyt Shaver oli huoneessa näyttänyt vierailleen ilmakivääriään, jota hän käytti lintujen torjuntaan. Ase oli ilmeisesti näkynyt ikkunasta hotellin allasalueelle, josta oli ilmoitettu asiasta vastaanottoon, joka puolestaan soitti poliisit paikalle."    
        );
        
        List<String> pictures = Arrays.asList(
                "miljonaari_etu71217TK_tw.jpg",
                "shaveretuJI091217_ul.jpg"
        );
        
        List<int[]> cats = Arrays.asList(
            new int[] {1, 3},
            new int[] {2}
        );
        
        LocalDateTime time = LocalDateTime.now();
        
        Random rnd = new Random();
        
        for(int i = 0; i < titles.size(); i++) {
            time = time.minus(Duration.of(rnd.nextInt(60 * 60 * 24 * 2), ChronoUnit.SECONDS));
            
            this.create(titles.get(i), leads.get(i), contents.get(i), pictures.get(i), cats.get(i), time, rnd.nextInt(1000));
        }
    }
}
