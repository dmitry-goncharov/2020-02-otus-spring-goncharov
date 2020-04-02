package ru.gonch.spring.service;

import org.springframework.stereotype.Service;
import ru.gonch.spring.dao.AuthorDao;
import ru.gonch.spring.dao.BookDao;
import ru.gonch.spring.dao.GenreDao;
import ru.gonch.spring.model.Book;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;

    public BookServiceImpl(BookDao bookDao, GenreDao genreDao, AuthorDao authorDao) {
        this.bookDao = bookDao;
        this.genreDao = genreDao;
        this.authorDao = authorDao;
    }

    @Override
    public long insert(Book book) {
        checkGenreIdNonEmpty(book);
        checkAuthorIdNonEmpty(book);
        return bookDao.insert(book);
    }

    @Override
    public List<Book> getAll(int limit, int offset) {
        return bookDao.getAll(limit, offset);
    }

    @Override
    public List<Book> getBooksByGenreId(long genreId, int limit, int offset) {
        return bookDao.getBooksByGenreId(genreId, limit, offset);
    }

    @Override
    public List<Book> getBooksByAuthorId(long authorId, int limit, int offset) {
        return bookDao.getBooksByAuthorId(authorId, limit, offset);
    }

    @Override
    public Optional<Book> getById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public boolean update(Book book) {
        checkGenreIdNonEmpty(book);
        checkAuthorIdNonEmpty(book);
        return bookDao.update(book) > 0;
    }

    @Override
    public boolean deleteById(long id) {
        return bookDao.deleteById(id) > 0;
    }

    private void checkAuthorIdNonEmpty(Book book) {
        if (authorDao.getById(book.getAuthor().getId()).isEmpty()) {
            throw new IllegalArgumentException("Incorrect author id");
        }
    }

    private void checkGenreIdNonEmpty(Book book) {
        if (genreDao.getById(book.getGenre().getId()).isEmpty()) {
            throw new IllegalArgumentException("Incorrect genre id");
        }
    }
}
