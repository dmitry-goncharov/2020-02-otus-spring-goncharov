package ru.gonch.spring.service;

import ru.gonch.spring.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    long save(Comment comment);

    List<Comment> getAll();

    List<Comment> getCommentsByBookId(long bookId);

    Optional<Comment> getById(long id);

    boolean update(Comment comment);

    boolean deleteById(long id);
}
