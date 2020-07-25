package ru.gonch.spring.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.hibernate.Hibernate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @HystrixCommand(commandKey = "saveCommentKey")
    @PreAuthorize("hasAnyRole('USER','EDITOR','ADMIN')")
    @Override
    public Comment save(Comment comment) {
        checkBookIdNonEmpty(comment);
        return commentRepository.save(comment);
    }

    @HystrixCommand(commandKey = "getCommentsKey")
    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @HystrixCommand(commandKey = "getCommentsByBookKey", commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
    })
    @Transactional
    @Override
    public List<Comment> getCommentsByBookId(long bookId) {
        return bookRepository.findById(bookId).map(book -> {
            Hibernate.initialize(book.getComments());
            return book.getComments();
        }).orElse(Collections.emptyList());
    }

    @HystrixCommand(commandKey = "getCommentKey")
    @Override
    public Optional<Comment> getById(long id) {
        return commentRepository.findById(id);
    }

    @HystrixCommand(commandKey = "deleteCommentKey")
    @PreAuthorize("hasAnyRole('EDITOR','ADMIN')")
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
