package com.jarnoluu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jarnoluu.domain.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    
}
