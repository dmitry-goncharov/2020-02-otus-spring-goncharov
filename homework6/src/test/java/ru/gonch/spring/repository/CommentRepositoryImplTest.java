package ru.gonch.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.gonch.spring.model.Comment;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(CommentRepositoryImpl.class)
class CommentRepositoryImplTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentRepositoryImpl commentRepository;

    @Test
    void insertTest() {
        Comment newComment = new Comment("Test From", "Test Comment", 1);
        long id = commentRepository.save(newComment);
        Comment comment = em.find(Comment.class, id);

        assertTrue(id > 0);
        assertEquals(newComment.getName(), comment.getName());
        assertEquals(newComment.getComment(), comment.getComment());
        assertEquals(newComment.getBookId(), comment.getBookId());
    }

    @Test
    void geAllTest() {
        List<Comment> comments = commentRepository.getAll();

        assertEquals(3, comments.size());
        assertEquals("From1", comments.get(0).getName());
        assertEquals("Comment1", comments.get(0).getComment());
        assertEquals(1L, comments.get(0).getBookId());
        assertEquals("From1", comments.get(1).getName());
        assertEquals("Comment2", comments.get(1).getComment());
        assertEquals(1L, comments.get(1).getBookId());
        assertEquals("From1", comments.get(2).getName());
        assertEquals("Comment1", comments.get(2).getComment());
        assertEquals(2L, comments.get(2).getBookId());
    }

    @Test
    void geCommentsByBookIdTest() {
        List<Comment> comments = commentRepository.getCommentsByBookId(1L);

        assertEquals(2, comments.size());
        assertEquals("From1", comments.get(0).getName());
        assertEquals("Comment1", comments.get(0).getComment());
        assertEquals("From1", comments.get(1).getName());
        assertEquals("Comment2", comments.get(1).getComment());
    }

    @Test
    void getByIdTest() {
        Optional<Comment> comment = commentRepository.getById(3);

        assertTrue(comment.isPresent());
        assertEquals("From1", comment.get().getName());
        assertEquals("Comment1", comment.get().getComment());
        assertEquals(2L, comment.get().getBookId());
    }

    @Test
    void updateTest() {
        Comment comment = em.find(Comment.class, 1L);
        em.detach(comment);

        commentRepository.update(new Comment(comment.getId(), "Updated From1", "Updated Comment1", 2L));

        Comment updatedComment = em.find(Comment.class, comment.getId());

        assertEquals("Updated From1", updatedComment.getName());
        assertEquals("Updated Comment1", updatedComment.getComment());
        assertEquals(2L, updatedComment.getBookId());
    }

    @Test
    void deleteByIdTest() {
        commentRepository.deleteById(1);

        assertNull(em.find(Comment.class, 1L));
    }
}
