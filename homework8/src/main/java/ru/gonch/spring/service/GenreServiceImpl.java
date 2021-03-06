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
    public String save(Genre genre) {
        return genreRepository.save(genre).getId();
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> getById(String id) {
        return genreRepository.findById(id);
    }

    @Override
    public boolean update(Genre genre) {
        if (genreRepository.existsById(genre.getId())) {
            genreRepository.save(genre);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(String id) {
        if (genreRepository.existsById(id)) {
            genreRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
