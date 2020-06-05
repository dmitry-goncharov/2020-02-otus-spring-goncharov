package ru.gonch.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import reactor.test.StepVerifier;
import ru.gonch.spring.model.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        Book book = bookRepository.findAll().blockFirst();
        assertNotNull(book);
        StepVerifier
                .create(commentRepository.getCommentsByBookId(book.getId()))
                .assertNext(comment -> {
                    assertEquals("From1", comment.getName());
                    assertEquals("Comment1", comment.getText());
                })
                .assertNext(comment -> {
                    assertEquals("From1", comment.getName());
                    assertEquals("Comment2", comment.getText());
                })
                .expectComplete()
                .verify();
    }
}
