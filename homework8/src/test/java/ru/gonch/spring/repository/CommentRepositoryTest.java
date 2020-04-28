package ru.gonch.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.gonch.spring.model.Comment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.gonch.spring.config", "ru.gonch.spring.repository"})
class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    void getCommentsByBookIdTest() {
        String bookId = bookRepository.findAll().get(0).getId();
        List<Comment> comments = commentRepository.getCommentsByBookId(bookId);

        assertEquals(2, comments.size());
        assertEquals("From1", comments.get(0).getName());
        assertEquals("Comment1", comments.get(0).getComment());
        assertEquals("From1", comments.get(1).getName());
        assertEquals("Comment2", comments.get(1).getComment());
    }
}
