package ru.gonch.spring.dao;

import ru.gonch.spring.model.Genre;

import java.util.List;

public interface GenreDao {
    long insert(Genre genre);

    List<Genre> getAll(int limit, int offset);

    Genre getById(long id);

    void update(Genre genre);

    void deleteById(long id);
}
