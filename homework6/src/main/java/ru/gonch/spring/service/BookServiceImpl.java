package ru.gonch.spring.service;

import org.springframework.stereotype.Service;
import ru.gonch.spring.model.Book;
import ru.gonch.spring.repository.AuthorRepository;
import ru.gonch.spring.repository.BookRepository;
import ru.gonch.spring.repository.GenreRepository;

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
    public List<Book> getAll(int limit, int offset) {
        return bookRepository.getAll(limit, offset);
    }

    @Override
    public List<Book> getBooksByGenreId(long genreId, int limit, int offset) {
        return bookRepository.getBooksByGenreId(genreId, limit, offset);
    }

    @Override
    public List<Book> getBooksByAuthorId(long authorId, int limit, int offset) {
        return bookRepository.getBooksByAuthorId(authorId, limit, offset);
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
        if (authorRepository.getById(book.getAuthor().getId()).isEmpty()) {
            throw new IllegalArgumentException("Incorrect author id");
        }
    }

    private void checkGenreIdNonEmpty(Book book) {
        if (genreRepository.getById(book.getGenre().getId()).isEmpty()) {
            throw new IllegalArgumentException("Incorrect genre id");
        }
    }
}
