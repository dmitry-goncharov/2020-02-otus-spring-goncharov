package ru.gonch.spring.service;

import ru.gonch.spring.model.Genre;

import java.util.List;

public interface GenreService {
    long insert(Genre genre);

    List<Genre> getAll(int limit, int offset);

    Genre getById(long id);

    void update(Genre genre);

    void deleteById(long id);
}
