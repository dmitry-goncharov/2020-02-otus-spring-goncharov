package ru.gonch.spring.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.gonch.spring.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {
    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Test
    void insertTest() {
        Genre newGenre = new Genre("Test Genre");
        long id = genreDaoJdbc.insert(newGenre);

        assertTrue(id > 0);
        assertEquals(newGenre.getName(), genreDaoJdbc.getById(id).orElseThrow().getName());
    }

    @Test
    void getAllTest() {
        List<Genre> genres = genreDaoJdbc.getAll(10, 0);

        assertEquals(5, genres.size());
    }

    @Test
    void getAllWithLimitTest() {
        List<Genre> genres = genreDaoJdbc.getAll(2, 0);

        assertEquals(2, genres.size());
        assertEquals("Novel", genres.get(0).getName());
        assertEquals("Story", genres.get(1).getName());
    }

    @Test
    void getAllWithLimitAndOffsetTest() {
        List<Genre> genres = genreDaoJdbc.getAll(10, 4);

        assertEquals(1, genres.size());
        assertEquals("Piece", genres.get(0).getName());
    }

    @Test
    void getByIdTest() {
        Optional<Genre> genre = genreDaoJdbc.getById(3);

        assertTrue(genre.isPresent());
        assertEquals("Prose", genre.get().getName());
    }

    @Test
    void updateTest() {
        genreDaoJdbc.update(new Genre(genreDaoJdbc.getById(1).orElseThrow().getId(), "Ode"));

        assertEquals("Ode", genreDaoJdbc.getById(1).orElseThrow().getName());
    }

    @Test
    void deleteByIdTest() {
        genreDaoJdbc.deleteById(1);

        assertEquals(4, genreDaoJdbc.getAll(10, 0).size());
    }
}
