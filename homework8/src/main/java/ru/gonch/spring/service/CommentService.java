package ru.gonch.spring.service;

import ru.gonch.spring.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    String save(Comment comment);

    List<Comment> getAll();

    List<Comment> getCommentsByBookId(String bookId);

    Optional<Comment> getById(String id);

    boolean update(Comment comment);

    boolean deleteById(String id);
}
