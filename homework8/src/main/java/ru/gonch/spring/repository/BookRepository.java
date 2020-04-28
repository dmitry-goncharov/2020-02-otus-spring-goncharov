package ru.gonch.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.gonch.spring.model.Book;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {
}
