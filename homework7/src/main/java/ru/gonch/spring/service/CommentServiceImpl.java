package ru.gonch.spring.service;

import org.hibernate.Hibernate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gonch.spring.model.Comment;
import ru.gonch.spring.repository.BookRepository;
import ru.gonch.spring.repository.CommentRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    public CommentServiceImpl(CommentRepository commentRepository,
                              BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public long save(Comment comment) {
        checkBookIdNonEmpty(comment);
        return commentRepository.save(comment).getId();
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Transactional
    @Override
    public List<Comment> getCommentsByBookId(long bookId) {
        return bookRepository.findById(bookId).map(book -> {
            Hibernate.initialize(book.getComments());
            return book.getComments();
        }).orElse(Collections.emptyList());
    }

    @Override
    public Optional<Comment> getById(long id) {
        return commentRepository.findById(id);
    }

    @Override
    public boolean update(Comment comment) {
        checkBookIdNonEmpty(comment);
        if (commentRepository.existsById(comment.getId())) {
            commentRepository.save(comment);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(long id) {
        try {
            commentRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    private void checkBookIdNonEmpty(Comment comment) {
        if (!bookRepository.existsById(comment.getBookId())) {
            throw new IllegalArgumentException("Incorrect book id");
        }
    }
}
