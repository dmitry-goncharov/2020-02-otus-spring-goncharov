package ru.gonch.spring.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Mongock mongock(MongoClient mongoClient, MongoProps mongoProps) {
        return new SpringMongockBuilder(
                mongoClient,
                mongoProps.getDatabase(),
                "ru.gonch.spring.changelog"
        ).build();
    }
}
