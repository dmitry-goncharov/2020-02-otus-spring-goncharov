package ru.gonch.spring.dao;

import ru.gonch.spring.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    long insert(Genre genre);

    List<Genre> getAll(int limit, int offset);

    Optional<Genre> getById(long id);

    int update(Genre genre);

    int deleteById(long id);
}
