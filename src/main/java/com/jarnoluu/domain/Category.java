package com.jarnoluu.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Category extends AbstractPersistable<Long> {
    String name;
    
    // ei voi olla LAZY koska artikkelien kuvadata on blob, joka voidaan ladata
    // vain transaktio kontekstissa -- tämän tiedon selvittelyyn ja pureskeluun
    // käytetty monta tuntia.
    @ManyToMany(mappedBy="categories", fetch=FetchType.EAGER)
    private List<Article> articles;
}
