package ru.gonch.spring.repository.mongo;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.gonch.spring.model.mongo.CommentMongo;

import java.util.List;

public class CommentMongoRepositoryCustomImpl implements CommentMongoRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    public CommentMongoRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<CommentMongo> findCommentsByBookId(String bookId) {
        Query query = Query.query(Criteria.where("book_id").is(bookId));
        return mongoTemplate.find(query, CommentMongo.class);
    }
}
