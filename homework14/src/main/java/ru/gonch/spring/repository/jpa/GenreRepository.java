package ru.gonch.spring.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gonch.spring.model.jpa.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
