package ru.gonch.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.gonch.spring.model.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String>, CommentServiceCustom {
}
