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
    public List<Author> getAll(int limit, int offset) {
        return em.createQuery("select a from Author a", Author.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Optional<Author> getById(long id) {
        try {
            return Optional.of(
                    em.createQuery("select a from Author a where a.id=:id", Author.class)
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
        return em.createQuery("update Author a set a.name=:name where a.id=:id")
                .setParameter("name", author.getName())
                .setParameter("id", author.getId())
                .executeUpdate();
    }

    @Transactional
    @Override
    public int deleteById(long id) {
        return em.createQuery("delete from Author a where a.id=:id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
