package com.jarnoluu.juutiset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jarnoluu.juutiset.domain.Article;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    public List<Article> findAllByPublishedAfterOrderByViewsDesc(LocalDateTime time);
}
