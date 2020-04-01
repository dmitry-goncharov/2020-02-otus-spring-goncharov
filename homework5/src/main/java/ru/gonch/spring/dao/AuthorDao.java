package ru.gonch.spring.dao;

import ru.gonch.spring.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    long insert(Author author);

    List<Author> getAll(int limit, int offset);

    Optional<Author> getById(long id);

    int update(Author author);

    int deleteById(long id);
}
