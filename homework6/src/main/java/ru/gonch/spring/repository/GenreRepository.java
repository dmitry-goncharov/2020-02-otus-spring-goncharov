package ru.gonch.spring.repository;

import ru.gonch.spring.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    long save(Genre genre);

    List<Genre> getAll(int limit, int offset);

    Optional<Genre> getById(long id);

    int update(Genre genre);

    int deleteById(long id);
}
