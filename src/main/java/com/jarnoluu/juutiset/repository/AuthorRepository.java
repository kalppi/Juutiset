package com.jarnoluu.juutiset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jarnoluu.juutiset.domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    public Author findByName(String name);
    public Author findByUsername(String username);
}
