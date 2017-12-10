package com.jarnoluu.juutiset.service;

import com.jarnoluu.juutiset.domain.Article;
import com.jarnoluu.juutiset.domain.Author;
import com.jarnoluu.juutiset.domain.Category;
import com.jarnoluu.juutiset.domain.Picture;
import com.jarnoluu.juutiset.repository.ArticleRepository;
import com.jarnoluu.juutiset.repository.AuthorRepository;
import com.jarnoluu.juutiset.repository.CategoryRepository;
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
import javax.transaction.Transactional;
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
    
    @Autowired
    private AuthorRepository authorRepository;
    
    private void create(String title, String lead, String content, String picture, List<Integer> categories, LocalDateTime time, int views, List<Integer> authors) throws IOException {
        Path path = Paths.get("pics/" + picture);
        byte[] data = Files.readAllBytes(path);
        
        Picture pic = new Picture(picture, "image/jpeg", (long)data.length, data);
        
        List<Category> cats = new ArrayList();
        for(int cat : categories) {
            cats.add(this.categoryRepository.getOne((long)cat));
        }
        
        Article article = newsService.publishArticle(title, lead, content, pic, cats);
        
        article.setPublished(time);
        article.setViews(views);
        
        List<Author> as = new ArrayList();
        for(int a : authors) {
            as.add(this.authorRepository.getOne((long)a));
        }
        
        article.setAuthors(as);
        
        this.articleRepository.save(article);
    }
    
    public void createTestData() throws IOException {
        final int CAT_COUNT = 3;
        final int AUT_COUNT = 3;
        
        this.categoryRepository.save(new Category("Kotimaa"));
        this.categoryRepository.save(new Category("Ulkomaat"));
        this.categoryRepository.save(new Category("Viihde"));
        
        this.authorRepository.save(new Author("kaap", "Kalle Appanen"));
        this.authorRepository.save(new Author("lale", "Lalli Leipäjuusto"));
        this.authorRepository.save(new Author("skele", "Skeletor"));
        
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
        
        LocalDateTime time = LocalDateTime.now();
        
        Random rnd = new Random();
        
        for(int j = 0; j < 25; j++) {
            time = time.minus(Duration.of(rnd.nextInt(60 * 60 * 24 * 2), ChronoUnit.SECONDS));

            List<Integer> cats = new ArrayList();
            for(int k = 0; k < rnd.nextInt(3) + 1; k++) {
                int cat = rnd.nextInt(CAT_COUNT) + 1;
                if(!cats.contains(cat)) cats.add(cat);
            }

            List<Integer> authors = new ArrayList();
            for(int k = 0; k < rnd.nextInt(3) + 1; k++) {
                int aut = CAT_COUNT + rnd.nextInt(AUT_COUNT) + 1;
                if(!authors.contains(aut)) authors.add(aut);
            }

            String title = titles.get(rnd.nextInt(titles.size()));
            String lead = leads.get(rnd.nextInt(leads.size()));
            String picture = pictures.get(rnd.nextInt(pictures.size()));
            String content = contents.get(rnd.nextInt(contents.size()));
            int views = rnd.nextInt(1000);

            this.create(title, lead, content, picture, cats, time, views, authors);
        }
    }
}
