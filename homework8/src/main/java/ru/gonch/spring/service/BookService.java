package ru.gonch.spring.service;

import ru.gonch.spring.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    String save(Book book);

    List<Book> getAll();

    List<Book> getBooksByGenreId(String genreId);

    List<Book> getBooksByAuthorId(String authorId);

    Optional<Book> getById(String id);

    boolean update(Book book);

    boolean deleteById(String id);
}
