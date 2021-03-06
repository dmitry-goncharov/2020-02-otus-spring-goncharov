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
    public String save(Author author) {
        return authorRepository.save(author).getId();
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> getById(String id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean update(Author author) {
        if (authorRepository.existsById(author.getId())) {
            authorRepository.save(author);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(String id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
