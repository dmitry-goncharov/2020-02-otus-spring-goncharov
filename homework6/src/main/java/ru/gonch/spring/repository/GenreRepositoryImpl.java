package ru.gonch.spring.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gonch.spring.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryImpl implements GenreRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public long save(Genre genre) {
        if (genre.getId() <= 0) {
            em.persist(genre);
            return genre.getId();
        } else {
            return em.merge(genre).getId();
        }
    }

    @Override
    public List<Genre> getAll(int limit, int offset) {
        return em.createQuery("select g from Genre g", Genre.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Optional<Genre> getById(long id) {
        try {
            return Optional.of(
                    em.createQuery("select g from Genre g where g.id=:id", Genre.class)
                            .setParameter("id", id)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public int update(Genre genre) {
        return em.createQuery("update Genre g set g.name=:name where g.id=:id")
                .setParameter("name", genre.getName())
                .setParameter("id", genre.getId())
                .executeUpdate();
    }

    @Transactional
    @Override
    public int deleteById(long id) {
        return em.createQuery("delete from Genre g where g.id=:id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
