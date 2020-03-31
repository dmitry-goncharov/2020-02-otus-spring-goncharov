package ru.gonch.spring.service;

import ru.gonch.spring.model.Author;

import java.util.List;

public interface AuthorService {
    long insert(Author author);

    List<Author> getAll(int limit, int offset);

    Author getById(long id);

    void update(Author author);

    void deleteById(long id);
}
