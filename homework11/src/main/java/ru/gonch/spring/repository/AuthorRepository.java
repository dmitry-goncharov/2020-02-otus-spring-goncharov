package ru.gonch.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.gonch.spring.model.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
