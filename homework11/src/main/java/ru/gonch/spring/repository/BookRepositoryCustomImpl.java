package ru.gonch.spring.repository;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import ru.gonch.spring.model.Book;

public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final ReactiveMongoTemplate mongoTemplate;

    public BookRepositoryCustomImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Flux<Book> getBooksByGenreId(String genreId) {
        Query query = Query.query(Criteria.where("genre_id").is(genreId));
        return mongoTemplate.find(query, Book.class);
    }

    @Override
    public Flux<Book> getBooksByAuthorId(String authorId) {
        Query query = Query.query(Criteria.where("author_id").is(authorId));
        return mongoTemplate.find(query, Book.class);
    }
}
