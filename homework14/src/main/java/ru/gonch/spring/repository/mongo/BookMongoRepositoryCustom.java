package ru.gonch.spring.repository.mongo;

import ru.gonch.spring.model.mongo.BookMongo;

import java.util.List;

public interface BookMongoRepositoryCustom {
    List<BookMongo> findBooksByGenreId(String genreId);

    List<BookMongo> findBooksByAuthorId(String authorId);
}
