package ru.gonch.spring.service;

import ru.gonch.spring.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book save(Book book);

    List<Book> getAll();

    List<Book> getBooksByGenreId(long genreId);

    List<Book> getBooksByAuthorId(long authorId);

    Optional<Book> getById(long id);

    boolean deleteById(long id);
}
