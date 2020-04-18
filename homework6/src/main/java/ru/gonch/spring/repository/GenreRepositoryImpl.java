package ru.gonch.spring.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gonch.spring.model.Genre;

import javax.persistence.EntityManager;
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
    public List<Genre> getAll() {
        return em.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    @Override
    public Optional<Genre> getById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Transactional
    @Override
    public int update(Genre genre) {
        if (em.find(Genre.class, genre.getId()) != null) {
            em.merge(genre);
            return 1;
        } else {
            return 0;
        }
    }

    @Transactional
    @Override
    public int deleteById(long id) {
        Genre genre = em.find(Genre.class, id);
        if (genre != null) {
            em.remove(genre);
            return 1;
        } else {
            return 0;
        }
    }
}
