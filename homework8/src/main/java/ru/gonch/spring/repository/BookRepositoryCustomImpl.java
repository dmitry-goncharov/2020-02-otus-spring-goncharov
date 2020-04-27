package ru.gonch.spring.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.gonch.spring.model.Book;

import java.util.List;

public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    public BookRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Book> getBooksByGenreId(String genreId) {
        Query query = Query.query(Criteria.where("genre_id").is(genreId));
        return mongoTemplate.find(query, Book.class);
    }

    @Override
    public List<Book> getBooksByAuthorId(String authorId) {
        Query query = Query.query(Criteria.where("author_id").is(authorId));
        return mongoTemplate.find(query, Book.class);
    }
}
