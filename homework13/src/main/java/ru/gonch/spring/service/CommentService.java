package ru.gonch.spring.service;

import ru.gonch.spring.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment save(Comment comment);

    List<Comment> getAll();

    List<Comment> getCommentsByBookId(long bookId);

    Optional<Comment> getById(long id);

    boolean deleteById(long id);
}
