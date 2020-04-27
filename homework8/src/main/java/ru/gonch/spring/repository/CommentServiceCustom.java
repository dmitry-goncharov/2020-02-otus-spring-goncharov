package ru.gonch.spring.repository;

import ru.gonch.spring.model.Comment;

import java.util.List;

public interface CommentServiceCustom {
    List<Comment> getCommentsByBookId(String bookId);
}
