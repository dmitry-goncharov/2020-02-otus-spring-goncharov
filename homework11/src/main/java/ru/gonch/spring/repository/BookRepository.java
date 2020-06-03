package ru.gonch.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.gonch.spring.model.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String>, BookRepositoryCustom {
}
