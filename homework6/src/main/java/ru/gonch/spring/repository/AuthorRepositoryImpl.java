package ru.gonch.spring.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gonch.spring.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public long save(Author author) {
        if (author.getId() <= 0) {
            em.persist(author);
            return author.getId();
        } else {
            return em.merge(author).getId();
        }
    }

    @Override
    public List<Author> getAll() {
        return em.createQuery("select a from Author a left join fetch a.books", Author.class)
                .setHint("javax.persistence.fetchgraph", em.getEntityGraph("graph.author.books"))
                .getResultList();
    }

    @Override
    public Optional<Author> getById(long id) {
        try {
            return Optional.of(
                    em.createQuery("select a from Author a left join fetch a.books where a.id=:id", Author.class)
                            .setHint("javax.persistence.fetchgraph", em.getEntityGraph("graph.author.books"))
                            .setParameter("id", id)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public int update(Author author) {
        if (em.find(Author.class, author.getId()) != null) {
            em.merge(author);
            return 1;
        } else {
            return 0;
        }
    }

    @Transactional
    @Override
    public int deleteById(long id) {
        Author author = em.find(Author.class, id);
        if (author != null) {
            em.remove(author);
            return 1;
        } else {
            return 0;
        }
    }
}
