package ru.gonch.spring.service;

import org.hibernate.Hibernate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gonch.spring.model.Book;
import ru.gonch.spring.repository.AuthorRepository;
import ru.gonch.spring.repository.BookRepository;
import ru.gonch.spring.repository.GenreRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           GenreRepository genreRepository,
                           AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Book save(Book book) {
        checkGenreIdNonEmpty(book);
        checkAuthorIdNonEmpty(book);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Transactional
    @Override
    public List<Book> getBooksByGenreId(long genreId) {
        return genreRepository.findById(genreId).map(genre -> {
            Hibernate.initialize(genre.getBooks());
            return genre.getBooks();
        }).orElse(Collections.emptyList());
    }

    @Transactional
    @Override
    public List<Book> getBooksByAuthorId(long authorId) {
        return authorRepository.findById(authorId).map(author -> {
            Hibernate.initialize(author.getBooks());
            return author.getBooks();
        }).orElse(Collections.emptyList());
    }

    @Override
    public Optional<Book> getById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public boolean deleteById(long id) {
        try {
            bookRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    private void checkAuthorIdNonEmpty(Book book) {
        if (!authorRepository.existsById(book.getAuthorId())) {
            throw new IllegalArgumentException("Incorrect author id");
        }
    }

    private void checkGenreIdNonEmpty(Book book) {
        if (!genreRepository.existsById(book.getGenreId())) {
            throw new IllegalArgumentException("Incorrect genre id");
        }
    }
}
