package ru.gonch.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gonch.spring.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
