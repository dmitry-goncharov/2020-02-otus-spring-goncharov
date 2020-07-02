package ru.gonch.spring.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gonch.spring.model.jpa.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
