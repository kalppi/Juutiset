package com.jarnoluu.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
public class Article extends AbstractPersistable<Long> {
    @NonNull
    private String title;
    
    @NonNull
    @Type(type="text")
    private String lead;
    
    @NonNull
    @Type(type="text")
    private String content;
    
    @NonNull
    @ManyToOne
    @JoinColumn(name="picture_id")
    private Picture picture;
    
    @NonNull
    @ManyToOne
    @JoinColumn(name="picture_thumb_id")
    private Picture thumb;
    
    @NonNull
    private LocalDateTime published;
    
    @NonNull
    @OneToMany
    List<Category> categories;
    
    @NonNull
    private Integer views;
    
    @NonNull
    @ManyToMany
    List<Author> authors;
    
    public List<Category> getCategories() {
        if (this.categories == null) {
            this.categories = new ArrayList<>();
        }

        return this.categories;
    }
    
    public List<Author> getAuthors() {
        if (this.authors == null) {
            this.authors = new ArrayList<>();
        }

        return this.authors;
    }
}
