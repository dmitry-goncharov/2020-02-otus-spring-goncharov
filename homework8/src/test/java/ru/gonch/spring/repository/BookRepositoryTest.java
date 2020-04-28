package ru.gonch.spring.repository;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.gonch.spring.model.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.gonch.spring.config", "ru.gonch.spring.repository", "ru.gonch.spring.event"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    @Order(1)
    void getBooksByGenreIdTest() {
        String genreId = genreRepository.findAll().get(0).getId();
        List<Book> books = bookRepository.getBooksByGenreId(genreId);

        assertEquals(1, books.size());
        assertEquals("Eugene Onegin", books.get(0).getName());
    }

    @Test
    @Order(2)
    void getBooksByAuthorIdTest() {
        String authorId = authorRepository.findAll().get(0).getId();
        List<Book> books = bookRepository.getBooksByAuthorId(authorId);

        assertEquals(1, books.size());
        assertEquals("Eugene Onegin", books.get(0).getName());
    }

    @Test
    @Order(3)
    void deleteBookCascadeTest() {
        Book book = bookRepository.findAll().get(0);

        assertFalse(commentRepository.getCommentsByBookId(book.getId()).isEmpty());

        bookRepository.delete(book);

        assertTrue(commentRepository.getCommentsByBookId(book.getId()).isEmpty());
    }
}
