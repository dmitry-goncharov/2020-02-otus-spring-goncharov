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
    public List<Comment> getCommentsByBookId(long bookId, int limit, int offset) {
        return em.createQuery("select c from Comment c where c.bookId=:bookId", Comment.class)
                .setParameter("bookId", bookId)
                .setMaxResults(limit)
                .setFirstResult(offset)
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
        return em.createQuery("update Comment c set c.name=:name,c.comment=:comment,c.bookId=:bookId where c.id=:id")
                .setParameter("name", comment.getName())
                .setParameter("comment", comment.getComment())
                .setParameter("bookId", comment.getBookId())
                .setParameter("id", comment.getId())
                .executeUpdate();
    }

    @Transactional
    @Override
    public int deleteById(long id) {
        return em.createQuery("delete from Comment c where c.id=:id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
