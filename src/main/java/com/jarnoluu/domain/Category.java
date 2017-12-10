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
public class Category extends AbstractPersistable<Long> {
    @NonNull
    String name;
    
    // ei voi olla LAZY koska artikkelien kuvadata on blob, joka voidaan ladata
    // vain transaktio kontekstissa -- tämän tiedon selvittelyyn ja pureskeluun
    // käytetty monta tuntia.
    @ManyToMany(mappedBy="categories", fetch=FetchType.EAGER)
    private List<Article> articles;
}
