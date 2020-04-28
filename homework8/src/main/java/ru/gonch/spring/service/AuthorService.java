package ru.gonch.spring.service;

import ru.gonch.spring.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    String save(Author author);

    List<Author> getAll();

    Optional<Author> getById(String id);

    boolean update(Author author);

    boolean deleteById(String id);
}
