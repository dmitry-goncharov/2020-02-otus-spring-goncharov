package ru.gonch.spring.repository;

import reactor.core.publisher.Flux;
import ru.gonch.spring.model.Book;

import java.util.List;

public interface BookRepositoryCustom {
    Flux<Book> getBooksByGenreId(String genreId);

    Flux<Book> getBooksByAuthorId(String authorId);
}
