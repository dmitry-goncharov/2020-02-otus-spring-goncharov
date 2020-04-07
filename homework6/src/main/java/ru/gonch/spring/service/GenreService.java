package ru.gonch.spring.service;

import ru.gonch.spring.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    long save(Genre genre);

    List<Genre> getAll(int limit, int offset);

    Optional<Genre> getById(long id);

    boolean update(Genre genre);

    boolean deleteById(long id);
}
