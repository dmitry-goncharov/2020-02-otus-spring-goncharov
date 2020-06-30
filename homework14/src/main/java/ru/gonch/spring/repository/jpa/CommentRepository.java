package ru.gonch.spring.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gonch.spring.model.jpa.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
