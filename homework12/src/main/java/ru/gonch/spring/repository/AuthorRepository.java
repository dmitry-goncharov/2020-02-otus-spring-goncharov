package ru.gonch.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gonch.spring.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
