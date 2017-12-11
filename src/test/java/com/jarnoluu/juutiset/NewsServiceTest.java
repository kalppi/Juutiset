package com.jarnoluu.juutiset;

import com.jarnoluu.juutiset.domain.Article;
import com.jarnoluu.juutiset.service.NewsService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsServiceTest {
    @Autowired
    private NewsService newsService;
    
    @Test
    public void testGetLatest() {
        List<Article> articles = this.newsService.getLatest(5);
        
        Assert.assertEquals(5, articles.size());
    }
    
    @Test
    public void testGetLatestPage() {
        List<Article> firstPage = this.newsService.getLatestPage(1, 5);
        List<Article> secondPage = this.newsService.getLatestPage(2, 5);
        
        List<Long> firstIds = firstPage.stream().map(Article::getId).collect(Collectors.toList());
        List<Long> secondIds = secondPage.stream().map(Article::getId).collect(Collectors.toList());
        
        firstIds.removeAll(secondIds);
        
        Assert.assertEquals(5, firstIds.size());
    }
    
    @Test
    public void testPopularViewsDescend() {
        List<Article> popular = this.newsService.getPopular();
        List<Integer> views = popular.stream().map(Article::getViews).collect(Collectors.toList());
        
        List<Integer> sorted = (ArrayList)((ArrayList)views).clone();
        Collections.sort(sorted);
        Collections.reverse(sorted);
        
        Assert.assertArrayEquals(sorted.toArray(), views.toArray());
    }
    
    @Test
    public void testPopularViewsWithinWeek() {
        List<Article> popular = this.newsService.getPopular();
        List<LocalDateTime> times = popular.stream().map(Article::getPublished).collect(Collectors.toList());
        
        LocalDateTime weekAgo = LocalDateTime.now().minusWeeks(1);
        
        for(LocalDateTime t : times) {
            Assert.assertTrue(t.isAfter(weekAgo));
        }
    }
}
