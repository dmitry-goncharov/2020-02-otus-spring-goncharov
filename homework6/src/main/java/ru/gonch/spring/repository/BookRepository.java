package ru.gonch.spring.repository;

import ru.gonch.spring.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    long save(Book book);

    List<Book> getAll(int limit, int offset);

    List<Book> getBooksByGenreId(long genreId, int limit, int offset);

    List<Book> getBooksByAuthorId(long authorId, int limit, int offset);

    Optional<Book> getById(long id);

    int update(Book book);

    int deleteById(long id);
}
