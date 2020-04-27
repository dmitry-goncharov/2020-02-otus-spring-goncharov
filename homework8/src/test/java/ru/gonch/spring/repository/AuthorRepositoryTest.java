package ru.gonch.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.gonch.spring.model.Author;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.gonch.spring.config", "ru.gonch.spring.repository", "ru.gonch.spring.event"})
class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    void deleteGenreCascadeTest() {
        Author author = authorRepository.findAll().get(3);

        assertFalse(bookRepository.getBooksByAuthorId(author.getId()).isEmpty());

        authorRepository.delete(author);

        assertTrue(bookRepository.getBooksByAuthorId(author.getId()).isEmpty());
    }
}
