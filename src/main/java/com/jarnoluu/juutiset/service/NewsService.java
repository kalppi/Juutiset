package com.jarnoluu.juutiset.service;

import com.jarnoluu.juutiset.domain.Article;
import com.jarnoluu.juutiset.domain.Author;
import com.jarnoluu.juutiset.domain.Category;
import com.jarnoluu.juutiset.domain.Picture;
import com.jarnoluu.juutiset.repository.ArticleRepository;
import com.jarnoluu.juutiset.repository.PictureRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class NewsService {
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private PictureRepository pictureRepository;
    
    @Autowired
    private PictureService pictureService;
    
    @Autowired
    private LoginService loginService;
    
    public Article editArticle(Article article, String title, String lead, String content) {
        article.setTitle(title);
        article.setLead(lead);
        article.setContent(content);
        
        Author author = this.loginService.getLoggedInAuthor();
        
        if(!article.getAuthors().stream().anyMatch(a -> a.getName().equals(author.getName()))) {
            article.getAuthors().add(author);
        }
        
        return this.articleRepository.save(article);
    }
    
    public Article publishArticle(String title, String lead, String content, Picture picture, List<Category> categories) throws IOException {
        this.pictureRepository.save(picture);
        
        Picture small = this.pictureService.createSmaller(picture);
        Picture thumb = this.pictureService.createThumb(picture);
        
        List<Author> authors = new ArrayList();
        authors.add(this.loginService.getLoggedInAuthor());
        
        Article article = new Article(title, lead, content, small, thumb, LocalDateTime.now(), categories, 0, authors);
        
        return this.articleRepository.save(article);
    }
    
    public List<Article> getLatest(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.Direction.DESC, "published");
        
        return this.articleRepository.findAll(pageable).getContent();
    }
    
    public List<Article> getPopular() {
        LocalDateTime time = LocalDateTime.now().minusWeeks(1);
        
        return this.articleRepository.findAllByPublishedAfterOrderByViewsDesc(time);
    }
}
