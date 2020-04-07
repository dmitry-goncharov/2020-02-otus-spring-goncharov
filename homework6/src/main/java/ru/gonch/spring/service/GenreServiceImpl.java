package ru.gonch.spring.service;

import org.springframework.stereotype.Service;
import ru.gonch.spring.model.Genre;
import ru.gonch.spring.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public long save(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public List<Genre> getAll(int limit, int offset) {
        return genreRepository.getAll(limit, offset);
    }

    @Override
    public Optional<Genre> getById(long id) {
        return genreRepository.getById(id);
    }

    @Override
    public boolean update(Genre genre) {
        return genreRepository.update(genre) > 0;
    }

    @Override
    public boolean deleteById(long id) {
        return genreRepository.deleteById(id) > 0;
    }
}
