package ru.gonch.spring.service;

import org.springframework.stereotype.Service;
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
    public long save(Comment comment) {
        checkBookIdNonEmpty(comment);
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.getAll();
    }

    @Override
    public List<Comment> getCommentsByBookId(long bookId) {
        return commentRepository.getCommentsByBookId(bookId);
    }

    @Override
    public Optional<Comment> getById(long id) {
        return commentRepository.getById(id);
    }

    @Override
    public boolean update(Comment comment) {
        checkBookIdNonEmpty(comment);
        return commentRepository.update(comment) > 0;
    }

    @Override
    public boolean deleteById(long id) {
        return commentRepository.deleteById(id) > 0;
    }

    private void checkBookIdNonEmpty(Comment comment) {
        if (bookRepository.getById(comment.getBookId()).isEmpty()) {
            throw new IllegalArgumentException("Incorrect book id");
        }
    }
}
