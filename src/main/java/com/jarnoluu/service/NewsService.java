package com.jarnoluu.service;

import com.jarnoluu.domain.Article;
import com.jarnoluu.domain.Picture;
import com.jarnoluu.repository.ArticleRepository;
import com.jarnoluu.repository.PictureRepository;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class NewsService {
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private PictureRepository pictureRepository;
    
    @Autowired
    private PictureService pictureService;
    
    @Transactional
    public Article publishArticle(String title, String lead, String content, Picture picture) throws IOException {
        this.pictureRepository.save(picture);
        
        Picture small = this.pictureService.createSmaller(picture, 250);
        Picture thumb = this.pictureService.createSmaller(picture, 100);
        
        Article article = new Article(title, lead, content, picture, small, thumb, new Date());
        
        return this.articleRepository.save(article);
    }
    
    public List<Article> getLatest() {
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "published");
        
        return this.articleRepository.findAll(pageable).getContent();
    }
}
