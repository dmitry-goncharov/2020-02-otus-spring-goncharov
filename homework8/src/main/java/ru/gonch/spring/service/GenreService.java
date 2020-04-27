package ru.gonch.spring.service;

import ru.gonch.spring.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    String save(Genre genre);

    List<Genre> getAll();

    Optional<Genre> getById(String id);

    boolean update(Genre genre);

    boolean deleteById(String id);
}
