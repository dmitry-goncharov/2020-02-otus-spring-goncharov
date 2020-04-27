package ru.gonch.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.gonch.spring.model.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
