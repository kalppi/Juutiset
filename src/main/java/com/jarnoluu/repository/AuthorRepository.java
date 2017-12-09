package com.jarnoluu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jarnoluu.domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    
}
