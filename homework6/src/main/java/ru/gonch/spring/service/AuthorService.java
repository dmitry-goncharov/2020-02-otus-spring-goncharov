package ru.gonch.spring.service;

import ru.gonch.spring.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    long save(Author author);

    List<Author> getAll(int limit, int offset);

    Optional<Author> getById(long id);

    boolean update(Author author);

    boolean deleteById(long id);
}
