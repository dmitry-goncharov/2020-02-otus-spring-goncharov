package ru.gonch.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.gonch.spring.model.Author;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class AuthorRepositoryTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void insertTest() {
        Author newAuthor = new Author();
        newAuthor.setName("Test Author");
        long id = authorRepository.save(newAuthor).getId();

        assertTrue(id > 0);
        assertEquals(newAuthor.getName(), em.find(Author.class, id).getName());
    }

    @Test
    void getAllTest() {
        List<Author> authors = authorRepository.findAll();

        assertEquals(5, authors.size());
        assertEquals("Pushkin", authors.get(0).getName());
        assertEquals("Turgenev", authors.get(1).getName());
        assertEquals("Dostoevsky", authors.get(2).getName());
        assertEquals("Chekhov", authors.get(3).getName());
        assertEquals("Sholokhov", authors.get(4).getName());
    }

    @Test
    void getByIdTest() {
        Optional<Author> author = authorRepository.findById(3L);

        assertTrue(author.isPresent());
        assertEquals("Dostoevsky", author.get().getName());
    }

    @Test
    void updateTest() {
        Author author = em.find(Author.class, 1L);
        em.detach(author);

        Author newAuthor = new Author();
        newAuthor.setId(author.getId());
        newAuthor.setName("A.S.Pushkin");
        authorRepository.save(newAuthor);

        assertEquals("A.S.Pushkin", em.find(Author.class, author.getId()).getName());
    }

    @Test
    void deleteByIdTest() {
        authorRepository.deleteById(1L);

        assertNull(em.find(Author.class, 1L));
    }
}
