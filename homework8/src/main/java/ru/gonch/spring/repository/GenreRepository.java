package ru.gonch.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.gonch.spring.model.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
}
