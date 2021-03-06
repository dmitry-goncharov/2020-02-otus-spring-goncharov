package ru.gonch.spring.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @HystrixCommand(commandKey = "saveGenreKey")
    @PreAuthorize("hasAnyRole('EDITOR','ADMIN')")
    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    @HystrixCommand(commandKey = "getGenresKey")
    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @HystrixCommand(commandKey = "getGenreKey")
    @Override
    public Optional<Genre> getById(long id) {
        return genreRepository.findById(id);
    }

    @HystrixCommand(commandKey = "deleteGenreKey")
    @PreAuthorize("hasAnyRole('EDITOR','ADMIN')")
    @Override
    public boolean deleteById(long id) {
        try {
            genreRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
