package ru.gonch.spring.repository;

import ru.gonch.spring.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    long save(Comment comment);

    List<Comment> getAll();

    Optional<Comment> getById(long id);

    int update(Comment comment);

    int deleteById(long id);
}
