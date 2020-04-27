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
    public String save(Book book) {
        checkGenreIdNonEmpty(book);
        checkAuthorIdNonEmpty(book);
        return bookRepository.save(book).getId();
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBooksByGenreId(String genreId) {
        return bookRepository.getBooksByGenreId(genreId);
    }

    @Override
    public List<Book> getBooksByAuthorId(String authorId) {
        return bookRepository.getBooksByAuthorId(authorId);
    }

    @Override
    public Optional<Book> getById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public boolean update(Book book) {
        checkGenreIdNonEmpty(book);
        checkAuthorIdNonEmpty(book);
        if (bookRepository.existsById(book.getId())) {
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(String id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
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
