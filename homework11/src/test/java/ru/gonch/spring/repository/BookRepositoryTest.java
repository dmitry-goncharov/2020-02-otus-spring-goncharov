package ru.gonch.spring.repository;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import reactor.test.StepVerifier;
import ru.gonch.spring.model.Author;
import ru.gonch.spring.model.Genre;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.gonch.spring.config", "ru.gonch.spring.repository"})
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void getBooksByGenreIdTest() {
        Genre genre = genreRepository.findAll().blockFirst();
        assertNotNull(genre);
        StepVerifier
                .create(bookRepository.getBooksByGenreId(genre.getId()))
                .assertNext(book -> assertEquals("Eugene Onegin", book.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    void getBooksByAuthorIdTest() {
        Author author = authorRepository.findAll().blockFirst();
        assertNotNull(author);
        StepVerifier
                .create(bookRepository.getBooksByAuthorId(author.getId()))
                .assertNext(book -> assertEquals("Eugene Onegin", book.getName()))
                .expectComplete()
                .verify();
    }
}
