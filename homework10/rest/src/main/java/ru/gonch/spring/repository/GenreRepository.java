package ru.gonch.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gonch.spring.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
