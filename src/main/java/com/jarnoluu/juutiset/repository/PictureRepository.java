package com.jarnoluu.juutiset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jarnoluu.juutiset.domain.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    
}
