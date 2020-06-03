package ru.gonch.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.gonch.spring.model.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}
