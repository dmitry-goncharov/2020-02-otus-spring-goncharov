package ru.gonch.spring.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gonch.spring.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public long save(Comment comment) {
        if (comment.getId() <= 0) {
            em.persist(comment);
            return comment.getId();
        } else {
            return em.merge(comment).getId();
        }
    }

    @Override
    public List<Comment> getAll() {
        return em.createQuery("select c from Comment c", Comment.class)
                .getResultList();
    }

    @Override
    public Optional<Comment> getById(long id) {
        try {
            return Optional.of(
                    em.createQuery("select c from Comment c where c.id=:id", Comment.class)
                            .setParameter("id", id)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public int update(Comment comment) {
        if (em.find(Comment.class, comment.getId()) != null) {
            em.merge(comment);
            return 1;
        } else {
            return 0;
        }
    }

    @Transactional
    @Override
    public int deleteById(long id) {
        Comment comment = em.find(Comment.class, id);
        if (comment != null) {
            em.remove(comment);
            return 1;
        } else {
            return 0;
        }
    }
}
