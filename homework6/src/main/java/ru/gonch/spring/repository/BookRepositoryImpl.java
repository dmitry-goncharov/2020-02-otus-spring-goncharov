package ru.gonch.spring.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gonch.spring.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
    public List<Book> getAll(int limit, int offset) {
        return em.createQuery(getAllBooksSql(), Book.class)
                .setHint("javax.persistence.fetchgraph", em.getEntityGraph("genre-author-books-graph"))
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public List<Book> getBooksByGenreId(long genreId, int limit, int offset) {
        return em.createQuery(getAllBooksSql() + " where g.id=:genreId", Book.class)
                .setHint("javax.persistence.fetchgraph", em.getEntityGraph("genre-author-books-graph"))
                .setParameter("genreId", genreId)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public List<Book> getBooksByAuthorId(long authorId, int limit, int offset) {
        return em.createQuery(getAllBooksSql() + " where a.id=:authorId", Book.class)
                .setHint("javax.persistence.fetchgraph", em.getEntityGraph("genre-author-books-graph"))
                .setParameter("authorId", authorId)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Optional<Book> getById(long id) {
        try {
            return Optional.of(
                    em.createQuery(getAllBooksSql() + " where b.id=:id", Book.class)
                            .setHint("javax.persistence.fetchgraph", em.getEntityGraph("genre-author-books-graph"))
                            .setParameter("id", id)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public int update(Book book) {
        return em.createQuery("update Book b set name=:name,genre_id=:genreId,author_id=:authorId where b.id=:id")
                .setParameter("name", book.getName())
                .setParameter("genreId", book.getGenre().getId())
                .setParameter("authorId", book.getAuthor().getId())
                .setParameter("id", book.getId())
                .executeUpdate();
    }

    @Transactional
    @Override
    public int deleteById(long id) {
        return em.createQuery("delete from Book b where b.id=:id")
                .setParameter("id", id)
                .executeUpdate();
    }

    private static String getAllBooksSql() {
        return "select b from Book b inner join fetch b.genre g inner join fetch b.author a";
    }
}
