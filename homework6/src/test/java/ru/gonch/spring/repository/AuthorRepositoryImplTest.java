package ru.gonch.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.gonch.spring.model.Author;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(AuthorRepositoryImpl.class)
class AuthorRepositoryImplTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private AuthorRepositoryImpl authorRepository;

    @Test
    void insertTest() {
        Author newAuthor = new Author("Test Author");
        long id = authorRepository.save(newAuthor);

        assertTrue(id > 0);
        assertEquals(newAuthor.getName(), em.find(Author.class, id).getName());
    }

    @Test
    void getAllTest() {
        List<Author> authors = authorRepository.getAll();

        assertEquals(5, authors.size());

        Author author1 = authors.get(0);
        assertEquals("Pushkin", author1.getName());
        assertEquals(1, author1.getBooks().size());
        assertEquals(2, new ArrayList<>(author1.getBooks()).get(0).getComments().size());

        Author author2 = authors.get(1);
        assertEquals("Turgenev", author2.getName());
        assertEquals(0, author2.getBooks().size());

        Author author3 = authors.get(2);
        assertEquals("Dostoevsky", author3.getName());
        assertEquals(0, author3.getBooks().size());

        Author author4 = authors.get(3);
        assertEquals("Chekhov", author4.getName());
        assertEquals(1, author4.getBooks().size());
        assertEquals(1, new ArrayList<>(author4.getBooks()).get(0).getComments().size());

        Author author5 = authors.get(4);
        assertEquals("Sholokhov", author5.getName());
        assertEquals(1, author5.getBooks().size());
        assertEquals(0, new ArrayList<>(author5.getBooks()).get(0).getComments().size());
    }

    @Test
    void getByIdTest() {
        Optional<Author> author = authorRepository.getById(3);

        assertTrue(author.isPresent());
        assertEquals("Dostoevsky", author.get().getName());
        assertEquals(0, author.get().getBooks().size());
    }

    @Test
    void updateTest() {
        Author author = em.find(Author.class, 1L);
        em.detach(author);

        authorRepository.update(new Author(author.getId(), "A.S.Pushkin"));

        assertEquals("A.S.Pushkin", em.find(Author.class, author.getId()).getName());
    }

    @Test
    void deleteByIdTest() {
        authorRepository.deleteById(1);

        assertNull(em.find(Author.class, 1L));
    }
}
