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

        Genre genre1 = genres.get(0);
        assertEquals("Novel", genre1.getName());
        assertEquals(1, genre1.getBooks().size());

        Genre genre2 = genres.get(1);
        assertEquals("Story", genre2.getName());
        assertEquals(1, genre2.getBooks().size());

        Genre genre3 = genres.get(2);
        assertEquals("Prose", genre3.getName());
        assertEquals(0, genre3.getBooks().size());

        Genre genre4 = genres.get(3);
        assertEquals("Novelette", genre4.getName());
        assertEquals(0, genre4.getBooks().size());

        Genre genre5 = genres.get(4);
        assertEquals("Piece", genre5.getName());
        assertEquals(1, genre5.getBooks().size());
    }

    @Test
    void getByIdTest() {
        Optional<Genre> genre = genreRepository.getById(3);

        assertTrue(genre.isPresent());
        assertEquals("Prose", genre.get().getName());
        assertEquals(0, genre.get().getBooks().size());
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
