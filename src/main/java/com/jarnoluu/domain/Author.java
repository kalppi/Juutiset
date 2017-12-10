package com.jarnoluu.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
public class Author extends AbstractPersistable<Long> {
    @NonNull
    String name;
    
    @ManyToMany(mappedBy="authors", fetch=FetchType.LAZY)
    private List<Article> articles;
}