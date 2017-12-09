package com.jarnoluu;

import javax.activation.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("production")
public class ProductionProfile {
    @Bean
    @ConfigurationProperties
    public DataSource dataSource() {
        return (DataSource) DataSourceBuilder.create().build();
    }
}