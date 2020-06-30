package ru.gonch.spring.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gonch.spring.model.jpa.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
