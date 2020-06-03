package ru.gonch.spring.repository;

import reactor.core.publisher.Flux;
import ru.gonch.spring.model.Comment;

public interface CommentServiceCustom {
    Flux<Comment> getCommentsByBookId(String bookId);
}
