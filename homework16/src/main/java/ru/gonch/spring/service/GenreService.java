package ru.gonch.spring.service;

import ru.gonch.spring.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    Genre save(Genre genre);

    List<Genre> getAll();

    Optional<Genre> getById(long id);

    boolean deleteById(long id);
}
