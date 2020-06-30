package ru.gonch.spring.repository.mongo;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.gonch.spring.model.mongo.BookMongo;

import java.util.List;

public class BookMongoRepositoryCustomImpl implements BookMongoRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    public BookMongoRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<BookMongo> findBooksByGenreId(String genreId) {
        Query query = Query.query(Criteria.where("genre_id").is(genreId));
        return mongoTemplate.find(query, BookMongo.class);
    }

    @Override
    public List<BookMongo> findBooksByAuthorId(String authorId) {
        Query query = Query.query(Criteria.where("author_id").is(authorId));
        return mongoTemplate.find(query, BookMongo.class);
    }
}
