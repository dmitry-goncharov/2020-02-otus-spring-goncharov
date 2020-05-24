package ru.gonch.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gonch.spring.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
