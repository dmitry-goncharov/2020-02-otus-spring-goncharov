package ru.gonch.spring.service;

import org.hibernate.Hibernate;
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
    public long save(Book book) {
        checkGenreIdNonEmpty(book);
        checkAuthorIdNonEmpty(book);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Transactional
    @Override
    public List<Book> getBooksByGenreId(long genreId) {
        return genreRepository.getById(genreId).map(genre -> {
            Hibernate.initialize(genre.getBooks());
            return genre.getBooks();
        }).orElse(Collections.emptyList());
    }

    @Transactional
    @Override
    public List<Book> getBooksByAuthorId(long authorId) {
        return authorRepository.getById(authorId).map(author -> {
            Hibernate.initialize(author.getBooks());
            return author.getBooks();
        }).orElse(Collections.emptyList());
    }

    @Override
    public Optional<Book> getById(long id) {
        return bookRepository.getById(id);
    }

    @Override
    public boolean update(Book book) {
        checkGenreIdNonEmpty(book);
        checkAuthorIdNonEmpty(book);
        return bookRepository.update(book) > 0;
    }

    @Override
    public boolean deleteById(long id) {
        return bookRepository.deleteById(id) > 0;
    }

    private void checkAuthorIdNonEmpty(Book book) {
        if (authorRepository.getById(book.getAuthorId()).isEmpty()) {
            throw new IllegalArgumentException("Incorrect author id");
        }
    }

    private void checkGenreIdNonEmpty(Book book) {
        if (genreRepository.getById(book.getGenreId()).isEmpty()) {
            throw new IllegalArgumentException("Incorrect genre id");
        }
    }
}
