package ru.gonch.spring.repository.mongo;

import ru.gonch.spring.model.mongo.CommentMongo;

import java.util.List;

public interface CommentMongoRepositoryCustom {
    List<CommentMongo> findCommentsByBookId(String bookId);
}
