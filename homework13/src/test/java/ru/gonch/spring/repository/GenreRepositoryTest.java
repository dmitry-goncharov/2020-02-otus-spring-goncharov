package ru.gonch.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.gonch.spring.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class GenreRepositoryTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void insertTest() {
        Genre newGenre = new Genre();
        newGenre.setName("Test Genre");
        long id = genreRepository.save(newGenre).getId();

        assertTrue(id > 0);
        assertEquals(newGenre.getName(), em.find(Genre.class, id).getName());
    }

    @Test
    void getAllTest() {
        List<Genre> genres = genreRepository.findAll();

        assertEquals(5, genres.size());
        assertEquals("Novel", genres.get(0).getName());
        assertEquals("Story", genres.get(1).getName());
        assertEquals("Prose", genres.get(2).getName());
        assertEquals("Novelette", genres.get(3).getName());
        assertEquals("Piece", genres.get(4).getName());
    }

    @Test
    void getByIdTest() {
        Optional<Genre> genre = genreRepository.findById(3L);

        assertTrue(genre.isPresent());
        assertEquals("Prose", genre.get().getName());
    }

    @Test
    void updateTest() {
        Genre genre = em.find(Genre.class, 1L);
        em.detach(genre);

        Genre newGenre = new Genre();
        newGenre.setId(genre.getId());
        newGenre.setName("Ode");
        genreRepository.save(newGenre);

        assertEquals("Ode", em.find(Genre.class, genre.getId()).getName());
    }

    @Test
    void deleteByIdTest() {
        genreRepository.deleteById(1L);

        assertNull(em.find(Genre.class, 1L));
    }
}
