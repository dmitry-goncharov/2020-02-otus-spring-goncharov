package ru.gonch.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.gonch.spring.model.Author;
import ru.gonch.spring.model.Book;
import ru.gonch.spring.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(BookRepositoryImpl.class)
class BookRepositoryImplTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookRepositoryImpl bookRepository;

    @Test
    void insertTest() {
        Book newBook = new Book("Test Book", em.find(Genre.class, 1L), em.find(Author.class, 1L));
        long id = bookRepository.save(newBook);

        assertTrue(id > 0);
        assertEquals(newBook.getName(), em.find(Book.class, id).getName());
    }

    @Test
    void getAllTest() {
        List<Book> books = bookRepository.getAll(10, 0);

        assertEquals(3, books.size());
        assertEquals("Eugene Onegin", books.get(0).getName());
        assertEquals("The Cherry Orchard", books.get(1).getName());
        assertEquals("Don stories", books.get(2).getName());
    }

    @Test
    void getAllWithLimitTest() {
        List<Book> books = bookRepository.getAll(2, 0);

        assertEquals(2, books.size());
        assertEquals("Eugene Onegin", books.get(0).getName());
        assertEquals("The Cherry Orchard", books.get(1).getName());
    }

    @Test
    void getAllWithLimitAndOffsetTest() {
        List<Book> books = bookRepository.getAll(1, 2);

        assertEquals(1, books.size());
        assertEquals("Don stories", books.get(0).getName());
    }

    @Test
    void getBooksByGenreId() {
        List<Book> books = bookRepository.getBooksByGenreId(1, 10, 0);

        assertEquals(1, books.size());
    }

    @Test
    void getBooksByGenreIdWithLimitTest() {
        List<Book> books = bookRepository.getBooksByGenreId(1, 2, 0);

        assertEquals(1, books.size());
        assertEquals("Eugene Onegin", books.get(0).getName());
    }

    @Test
    void getBooksByGenreIdWithLimitAndOffsetTest() {
        List<Book> books = bookRepository.getBooksByGenreId(1, 10, 2);

        assertEquals(0, books.size());
    }

    @Test
    void getBooksByAuthorIdTest() {
        List<Book> books = bookRepository.getBooksByAuthorId(1, 10, 0);

        assertEquals(1, books.size());
    }

    @Test
    void getBooksByAuthorIdWithLimitTest() {
        List<Book> books = bookRepository.getBooksByAuthorId(1, 2, 0);

        assertEquals(1, books.size());
        assertEquals("Eugene Onegin", books.get(0).getName());
    }

    @Test
    void getBooksByAuthorIdWithLimitAndOffsetTest() {
        List<Book> books = bookRepository.getBooksByAuthorId(1, 10, 2);

        assertEquals(0, books.size());
    }

    @Test
    void getByIdTest() {
        Optional<Book> book = bookRepository.getById(3);

        assertTrue(book.isPresent());
        assertEquals("Don stories", book.get().getName());
    }

    @Test
    void updateTest() {
        Book book = em.find(Book.class, 1L);
        em.detach(book);

        bookRepository.update(new Book(book.getId(), "A.S.Pushkin Eugene Onegin", book.getGenre(), book.getAuthor()));

        assertEquals("A.S.Pushkin Eugene Onegin", em.find(Book.class, book.getId()).getName());
    }

    @Test
    void deleteByIdTest() {
        bookRepository.deleteById(1);

        assertNull(em.find(Book.class, 1L));
    }
}
