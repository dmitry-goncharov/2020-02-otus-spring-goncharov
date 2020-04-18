package ru.gonch.spring.repository;

import ru.gonch.spring.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    long save(Author author);

    List<Author> getAll();

    Optional<Author> getById(long id);

    int update(Author author);

    int deleteById(long id);
}
