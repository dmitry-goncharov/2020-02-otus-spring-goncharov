package ru.gonch.spring.repository;

import ru.gonch.spring.model.Book;

import java.util.List;

public interface BookRepositoryCustom {
    List<Book> getBooksByGenreId(String genreId);

    List<Book> getBooksByAuthorId(String authorId);
}
