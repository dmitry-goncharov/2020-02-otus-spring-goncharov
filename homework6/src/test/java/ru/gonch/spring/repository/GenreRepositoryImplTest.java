package ru.gonch.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.gonch.spring.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(GenreRepositoryImpl.class)
class GenreRepositoryImplTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private GenreRepositoryImpl genreRepository;

    @Test
    void insertTest() {
        Genre newGenre = new Genre("Test Genre");
        long id = genreRepository.save(newGenre);

        assertTrue(id > 0);
        assertEquals(newGenre.getName(), em.find(Genre.class, id).getName());
    }

    @Test
    void insertTest2() {
        Genre newGenre = new Genre(6L, "Test Genre");
        long id = genreRepository.save(newGenre);

        assertTrue(id > 0);
        assertEquals(newGenre.getName(), em.find(Genre.class, id).getName());

        em.detach(newGenre);
        assertEquals(newGenre.getName(), em.find(Genre.class, id).getName());

    }

    @Test
    void getAllTest() {
        List<Genre> genres = genreRepository.getAll();

        assertEquals(5, genres.size());
        assertEquals("Novel", genres.get(0).getName());
        assertEquals("Story", genres.get(1).getName());
        assertEquals("Prose", genres.get(2).getName());
        assertEquals("Novelette", genres.get(3).getName());
        assertEquals("Piece", genres.get(4).getName());
    }

    @Test
    void getByIdTest() {
        Optional<Genre> genre = genreRepository.getById(3);

        assertTrue(genre.isPresent());
        assertEquals("Prose", genre.get().getName());
    }

    @Test
    void updateTest() {
        Genre genre = em.find(Genre.class, 1L);
        em.detach(genre);

        genreRepository.update(new Genre(genre.getId(), "Ode"));

        assertEquals("Ode", em.find(Genre.class, genre.getId()).getName());
    }

    @Test
    void deleteByIdTest() {
        genreRepository.deleteById(1);

        assertNull(em.find(Genre.class, 1L));
    }
}
