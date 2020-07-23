package ru.gonch.spring.actuate;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import ru.gonch.spring.repository.AuthorRepository;
import ru.gonch.spring.repository.BookRepository;
import ru.gonch.spring.repository.GenreRepository;

import java.util.Map;

@Component
@Endpoint(id = "content")
public class ContentInfoEndpoint {
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public ContentInfoEndpoint(GenreRepository genreRepository,
                               AuthorRepository authorRepository,
                               BookRepository bookRepository) {
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @ReadOperation
    public Map<String, Long> contentInfo() {
        return Map.of(
                "genres", genreRepository.count(),
                "authors", authorRepository.count(),
                "books", bookRepository.count()
        );
    }
}
