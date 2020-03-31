package ru.gonch.spring.dao;

import ru.gonch.spring.model.Book;

import java.util.List;

public interface BookDao {
    long insert(Book book);

    List<Book> getAll(int limit, int offset);

    List<Book> getBooksByGenreId(long genreId, int limit, int offset);

    List<Book> getBooksByAuthorId(long authorId, int limit, int offset);

    Book getById(long id);

    void update(Book book);

    void deleteById(long id);
}
