package ru.gonch.spring.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.gonch.spring.model.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {
    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    void insertTest() {
        Author newAuthor = new Author("Test Author");
        long id = authorDaoJdbc.insert(newAuthor);

        assertTrue(id > 0);
        assertEquals(newAuthor.getName(), authorDaoJdbc.getById(id).getName());
    }

    @Test
    void getAllTest() {
        List<Author> authors = authorDaoJdbc.getAll(10, 0);

        assertEquals(5, authors.size());
    }

    @Test
    void getAllWithLimitTest() {
        List<Author> authors = authorDaoJdbc.getAll(2, 0);

        assertEquals(2, authors.size());
        assertEquals("Pushkin", authors.get(0).getName());
        assertEquals("Turgenev", authors.get(1).getName());
    }

    @Test
    void getAllWithLimitAndOffsetTest() {
        List<Author> authors = authorDaoJdbc.getAll(10, 4);

        assertEquals(1, authors.size());
        assertEquals("Sholokhov", authors.get(0).getName());
    }

    @Test
    void getByIdTest() {
        Author author = authorDaoJdbc.getById(3);

        assertEquals("Dostoevsky", author.getName());
    }

    @Test
    void updateTest() {
        authorDaoJdbc.update(new Author(authorDaoJdbc.getById(1).getId(), "A.S.Pushkin"));

        assertEquals("A.S.Pushkin", authorDaoJdbc.getById(1).getName());
    }

    @Test
    void deleteByIdTest() {
        authorDaoJdbc.deleteById(1);

        assertEquals(4, authorDaoJdbc.getAll(10, 0).size());
    }
}
