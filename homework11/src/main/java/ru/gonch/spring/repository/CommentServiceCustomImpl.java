package ru.gonch.spring.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import ru.gonch.spring.model.Comment;

import java.util.List;

public class CommentServiceCustomImpl implements CommentServiceCustom {
    private final ReactiveMongoTemplate mongoTemplate;

    public CommentServiceCustomImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Flux<Comment> getCommentsByBookId(String bookId) {
        Query query = Query.query(Criteria.where("book_id").is(bookId));
        return mongoTemplate.find(query, Comment.class);
    }
}
