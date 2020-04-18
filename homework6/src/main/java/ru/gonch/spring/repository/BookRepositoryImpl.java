package ru.gonch.spring.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gonch.spring.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public long save(Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
            return book.getId();
        } else {
            return em.merge(book).getId();
        }
    }

    @Override
    public List<Book> getAll() {
        return em.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Transactional
    @Override
    public int update(Book book) {
        if (em.find(Book.class, book.getId()) != null) {
            em.merge(book);
            return 1;
        } else {
            return 0;
        }
    }

    @Transactional
    @Override
    public int deleteById(long id) {
        Book book = em.find(Book.class, id);
        if (book != null) {
            em.remove(book);
            return 1;
        } else {
            return 0;
        }
    }
}
