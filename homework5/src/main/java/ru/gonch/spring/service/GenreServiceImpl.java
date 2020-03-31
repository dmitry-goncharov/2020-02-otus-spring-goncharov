package ru.gonch.spring.service;

import org.springframework.stereotype.Service;
import ru.gonch.spring.dao.GenreDao;
import ru.gonch.spring.model.Genre;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public long insert(Genre genre) {
        return genreDao.insert(genre);
    }

    @Override
    public List<Genre> getAll(int limit, int offset) {
        return genreDao.getAll(limit, offset);
    }

    @Override
    public Genre getById(long id) {
        return genreDao.getById(id);
    }

    @Override
    public void update(Genre genre) {
        genreDao.update(genre);
    }

    @Override
    public void deleteById(long id) {
        genreDao.deleteById(id);
    }
}
