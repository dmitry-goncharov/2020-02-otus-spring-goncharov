package ru.gonch.spring.service;

import ru.gonch.spring.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author save(Author author);

    List<Author> getAll();

    Optional<Author> getById(long id);

    boolean deleteById(long id);
}
