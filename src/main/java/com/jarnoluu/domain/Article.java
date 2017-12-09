package com.jarnoluu.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Article extends AbstractPersistable<Long> {
    private String title;
    
    @Type(type="text")
    private String lead;
    
    @Type(type="text")
    private String content;
    
    @ManyToOne
    @JoinColumn(name="picture_id")
    private Picture picture;
    
    @ManyToOne
    @JoinColumn(name="picture_small_id")
    private Picture small;
    
    @ManyToOne
    @JoinColumn(name="picture_thumb_id")
    private Picture thumb;
    
    private LocalDateTime published;
    
    @OneToMany
    List<Category> categories;
    
    private int views;
    
    public List<Category> getCategories() {
        if (this.categories == null) {
            this.categories = new ArrayList<>();
        }

        return this.categories;
    }
}
