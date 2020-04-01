package ru.gonch.spring.service;

import org.springframework.stereotype.Service;
import ru.gonch.spring.dao.AuthorDao;
import ru.gonch.spring.model.Author;

import java.util.List;
import java.util.Optional;

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
    public Optional<Author> getById(long id) {
        return authorDao.getById(id);
    }

    @Override
    public boolean update(Author author) {
        return authorDao.update(author) > 0;
    }

    @Override
    public boolean deleteById(long id) {
        return authorDao.deleteById(id) > 0;
    }
}
