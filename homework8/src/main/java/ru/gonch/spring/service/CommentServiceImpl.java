package ru.gonch.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gonch.spring.model.Comment;
import ru.gonch.spring.repository.BookRepository;
import ru.gonch.spring.repository.CommentRepository;

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
    public String save(Comment comment) {
        checkBookIdNonEmpty(comment);
        return commentRepository.save(comment).getId();
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Transactional
    @Override
    public List<Comment> getCommentsByBookId(String bookId) {
        return commentRepository.getCommentsByBookId(bookId);
    }

    @Override
    public Optional<Comment> getById(String id) {
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
    public boolean deleteById(String id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void checkBookIdNonEmpty(Comment comment) {
        if (!bookRepository.existsById(comment.getBookId())) {
            throw new IllegalArgumentException("Incorrect book id");
        }
    }
}
