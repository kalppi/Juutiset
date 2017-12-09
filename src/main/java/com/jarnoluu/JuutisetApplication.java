package com.jarnoluu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.jarnoluu.repository")
public class JuutisetApplication {
    public static void main(String[] args) {
        SpringApplication.run(JuutisetApplication.class, args);
    }
}
