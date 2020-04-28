package ru.gonch.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.gonch.spring.model.Genre;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.gonch.spring.config", "ru.gonch.spring.repository", "ru.gonch.spring.event"})
class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    void deleteGenreCascadeTest() {
        Genre genre = genreRepository.findAll().get(0);

        assertFalse(bookRepository.getBooksByGenreId(genre.getId()).isEmpty());

        genreRepository.delete(genre);

        assertTrue(bookRepository.getBooksByGenreId(genre.getId()).isEmpty());
    }
}
