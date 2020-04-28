package ru.gonch.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.gonch.spring.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, String>, CommentServiceCustom {
}
