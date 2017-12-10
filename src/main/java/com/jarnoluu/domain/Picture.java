package com.jarnoluu.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
public class Picture extends AbstractPersistable<Long> {
    @NonNull
    private String name;
    
    @NonNull
    private String mediaType;
    
    @NonNull
    private Integer size;
    
    @NonNull
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;
}
