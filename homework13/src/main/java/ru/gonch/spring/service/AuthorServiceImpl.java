package ru.gonch.spring.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.gonch.spring.model.Author;
import ru.gonch.spring.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @PreAuthorize("hasAnyRole('EDITOR','ADMIN')")
    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> getById(long id) {
        return authorRepository.findById(id);
    }

    @PreAuthorize("hasAnyRole('EDITOR','ADMIN')")
    @Override
    public boolean deleteById(long id) {
        try {
            authorRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
