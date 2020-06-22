package ru.gonch.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.gonch.spring.model.Comment;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void insertTest() {
        Comment newComment = new Comment();
        newComment.setName("Test From");
        newComment.setText("Test Comment");
        newComment.setBookId(1L);

        long id = commentRepository.save(newComment).getId();
        Comment comment = em.find(Comment.class, id);

        assertTrue(id > 0);
        assertEquals(newComment.getName(), comment.getName());
        assertEquals(newComment.getText(), comment.getText());
        assertEquals(newComment.getBookId(), comment.getBookId());
    }

    @Test
    void geAllTest() {
        List<Comment> comments = commentRepository.findAll();

        assertEquals(3, comments.size());
        assertEquals("From1", comments.get(0).getName());
        assertEquals("Comment1", comments.get(0).getText());
        assertEquals(1L, comments.get(0).getBookId());
        assertEquals("From1", comments.get(1).getName());
        assertEquals("Comment2", comments.get(1).getText());
        assertEquals(1L, comments.get(1).getBookId());
        assertEquals("From1", comments.get(2).getName());
        assertEquals("Comment1", comments.get(2).getText());
        assertEquals(2L, comments.get(2).getBookId());
    }

    @Test
    void getByIdTest() {
        Optional<Comment> comment = commentRepository.findById(3L);

        assertTrue(comment.isPresent());
        assertEquals("From1", comment.get().getName());
        assertEquals("Comment1", comment.get().getText());
        assertEquals(2L, comment.get().getBookId());
    }

    @Test
    void updateTest() {
        Comment comment = em.find(Comment.class, 1L);
        em.detach(comment);

        Comment newComment = new Comment();
        newComment.setId(comment.getId());
        newComment.setName("Updated From1");
        newComment.setText("Updated Comment1");
        newComment.setBookId(2L);
        commentRepository.save(newComment);

        Comment updatedComment = em.find(Comment.class, comment.getId());

        assertEquals("Updated From1", updatedComment.getName());
        assertEquals("Updated Comment1", updatedComment.getText());
        assertEquals(2L, updatedComment.getBookId());
    }

    @Test
    void deleteByIdTest() {
        commentRepository.deleteById(1L);

        assertNull(em.find(Comment.class, 1L));
    }
}
