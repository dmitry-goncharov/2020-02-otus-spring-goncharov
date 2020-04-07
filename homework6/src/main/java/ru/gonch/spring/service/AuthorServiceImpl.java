package ru.gonch.spring.service;

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

    @Override
    public long save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Author> getAll(int limit, int offset) {
        return authorRepository.getAll(limit, offset);
    }

    @Override
    public Optional<Author> getById(long id) {
        return authorRepository.getById(id);
    }

    @Override
    public boolean update(Author author) {
        return authorRepository.update(author) > 0;
    }

    @Override
    public boolean deleteById(long id) {
        return authorRepository.deleteById(id) > 0;
    }
}
