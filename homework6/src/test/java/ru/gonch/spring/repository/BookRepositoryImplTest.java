package ru.gonch.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.gonch.spring.model.Book;

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
        Book newBook = new Book("Test Book", 1L, 1L);
        long id = bookRepository.save(newBook);

        assertTrue(id > 0);
        assertEquals(newBook.getName(), em.find(Book.class, id).getName());
    }

    @Test
    void getAllTest() {
        List<Book> books = bookRepository.getAll();

        assertEquals(3, books.size());

        Book book1 = books.get(0);
        assertEquals("Eugene Onegin", book1.getName());
        assertEquals(2, book1.getComments().size());

        Book book2 = books.get(1);
        assertEquals("The Cherry Orchard", book2.getName());
        assertEquals(1, book2.getComments().size());

        Book book3 = books.get(2);
        assertEquals("Don stories", book3.getName());
        assertEquals(0, book3.getComments().size());
    }

    @Test
    void getByIdTest() {
        Optional<Book> book = bookRepository.getById(3);

        assertTrue(book.isPresent());
        assertEquals("Don stories", book.get().getName());
        assertEquals(0, book.get().getComments().size());
    }

    @Test
    void updateTest() {
        Book book = em.find(Book.class, 1L);
        em.detach(book);

        bookRepository.update(new Book(book.getId(), "A.S.Pushkin Eugene Onegin", book.getGenreId(), book.getAuthorId()));

        assertEquals("A.S.Pushkin Eugene Onegin", em.find(Book.class, book.getId()).getName());
    }

    @Test
    void deleteByIdTest() {
        bookRepository.deleteById(1);

        assertNull(em.find(Book.class, 1L));
    }
}
