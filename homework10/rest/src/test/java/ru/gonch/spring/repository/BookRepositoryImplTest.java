package ru.gonch.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.gonch.spring.model.Book;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class BookRepositoryImplTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void insertTest() {
        Book newBook = new Book();
        newBook.setName("Test Book");
        newBook.setGenreId(1L);
        newBook.setAuthorId(1L);
        long id = bookRepository.save(newBook).getId();

        assertTrue(id > 0);
        assertEquals(newBook.getName(), em.find(Book.class, id).getName());
    }

    @Test
    void getAllTest() {
        List<Book> books = bookRepository.findAll();

        assertEquals(3, books.size());
        assertEquals("Eugene Onegin", books.get(0).getName());
        assertEquals("The Cherry Orchard", books.get(1).getName());
        assertEquals("Don stories", books.get(2).getName());
    }

    @Test
    void getByIdTest() {
        Optional<Book> book = bookRepository.findById(3L);

        assertTrue(book.isPresent());
        assertEquals("Don stories", book.get().getName());
    }

    @Test
    void updateTest() {
        Book book = em.find(Book.class, 1L);
        em.detach(book);

        Book newBook = new Book();
        newBook.setId(book.getId());
        newBook.setName("A.S.Pushkin Eugene Onegin");
        newBook.setGenreId(book.getGenreId());
        newBook.setAuthorId(book.getAuthorId());
        bookRepository.save(newBook);

        assertEquals("A.S.Pushkin Eugene Onegin", em.find(Book.class, book.getId()).getName());
    }

    @Test
    void deleteByIdTest() {
        bookRepository.deleteById(1L);

        assertNull(em.find(Book.class, 1L));
    }
}
