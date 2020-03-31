package ru.gonch.spring.service;

import org.springframework.stereotype.Service;
import ru.gonch.spring.dao.AuthorDao;
import ru.gonch.spring.model.Author;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public long insert(Author author) {
        return authorDao.insert(author);
    }

    @Override
    public List<Author> getAll(int limit, int offset) {
        return authorDao.getAll(limit, offset);
    }

    @Override
    public Author getById(long id) {
        return authorDao.getById(id);
    }

    @Override
    public void update(Author author) {
        authorDao.update(author);
    }

    @Override
    public void deleteById(long id) {
        authorDao.deleteById(id);
    }
}
