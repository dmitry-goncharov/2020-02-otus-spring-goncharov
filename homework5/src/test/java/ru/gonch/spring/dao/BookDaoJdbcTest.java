package ru.gonch.spring.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.gonch.spring.model.Book;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import({BookDaoJdbc.class, GenreDaoJdbc.class, AuthorDaoJdbc.class})
class BookDaoJdbcTest {
    @Autowired
    private BookDaoJdbc bookDaoJdbc;
    @Autowired
    private GenreDaoJdbc genreDaoJdbc;
    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    void insertTest() {
        Book newBook = new Book("Test Book", genreDaoJdbc.getById(1).orElseThrow(), authorDaoJdbc.getById(1).orElseThrow());
        long id = bookDaoJdbc.insert(newBook);

        assertTrue(id > 0);
        assertEquals(newBook.getName(), bookDaoJdbc.getById(id).orElseThrow().getName());
    }

    @Test
    void getAllTest() {
        List<Book> books = bookDaoJdbc.getAll(10, 0);

        assertEquals(3, books.size());
    }

    @Test
    void getAllWithLimitTest() {
        List<Book> books = bookDaoJdbc.getAll(2, 0);

        assertEquals(2, books.size());
        assertEquals("Eugene Onegin", books.get(0).getName());
        assertEquals("The Cherry Orchard", books.get(1).getName());
    }

    @Test
    void getAllWithLimitAndOffsetTest() {
        List<Book> books = bookDaoJdbc.getAll(10, 2);

        assertEquals(1, books.size());
        assertEquals("Don stories", books.get(0).getName());
    }

    @Test
    void getBooksByGenreId() {
        List<Book> books = bookDaoJdbc.getBooksByGenreId(1, 10, 0);

        assertEquals(1, books.size());
    }

    @Test
    void getBooksByGenreIdWithLimitTest() {
        List<Book> books = bookDaoJdbc.getBooksByGenreId(1, 2, 0);

        assertEquals(1, books.size());
        assertEquals("Eugene Onegin", books.get(0).getName());
    }

    @Test
    void getBooksByGenreIdWithLimitAndOffsetTest() {
        List<Book> books = bookDaoJdbc.getBooksByGenreId(1, 10, 2);

        assertEquals(0, books.size());
    }

    @Test
    void getBooksByAuthorIdTest() {
        List<Book> books = bookDaoJdbc.getBooksByAuthorId(1, 10, 0);

        assertEquals(1, books.size());
    }

    @Test
    void getBooksByAuthorIdWithLimitTest() {
        List<Book> books = bookDaoJdbc.getBooksByAuthorId(1, 2, 0);

        assertEquals(1, books.size());
        assertEquals("Eugene Onegin", books.get(0).getName());
    }

    @Test
    void getBooksByAuthorIdWithLimitAndOffsetTest() {
        List<Book> books = bookDaoJdbc.getBooksByAuthorId(1, 10, 2);

        assertEquals(0, books.size());
    }

    @Test
    void getByIdTest() {
        Optional<Book> book = bookDaoJdbc.getById(3);

        assertTrue(book.isPresent());
        assertEquals("Don stories", book.get().getName());
    }

    @Test
    void updateTest() {
        Book book = bookDaoJdbc.getById(1).orElseThrow();
        bookDaoJdbc.update(new Book(book.getId(), "A.S.Pushkin Eugene Onegin", book.getGenre(), book.getAuthor()));

        assertEquals("A.S.Pushkin Eugene Onegin", bookDaoJdbc.getById(1).orElseThrow().getName());
    }

    @Test
    void deleteByIdTest() {
        bookDaoJdbc.deleteById(1);

        assertEquals(2, bookDaoJdbc.getAll(10, 0).size());
    }
}
