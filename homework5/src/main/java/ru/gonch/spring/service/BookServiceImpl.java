package ru.gonch.spring.service;

import org.springframework.stereotype.Service;
import ru.gonch.spring.dao.BookDao;
import ru.gonch.spring.model.Book;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public long insert(Book book) {
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
    public Book getById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public void update(Book book) {
        bookDao.update(book);
    }

    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }
}
