package ru.gonch.spring.handler;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.gonch.spring.model.Genre;
import ru.gonch.spring.repository.BookRepository;
import ru.gonch.spring.repository.CommentRepository;
import ru.gonch.spring.repository.GenreRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class GenreHandler extends ValidationHandler<Genre> {
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    public GenreHandler(Validator validator,
                        GenreRepository genreRepository,
                        BookRepository bookRepository,
                        CommentRepository commentRepository) {
        super(validator);
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    @NonNull
    public Mono<ServerResponse> list(ServerRequest req) {
        return ok().contentType(APPLICATION_JSON).body(genreRepository.findAll(), Genre.class);
    }

    @NonNull
    public Mono<ServerResponse> one(ServerRequest req) {
        return genreRepository
                .findById(req.pathVariable("id"))
                .flatMap(genre -> ok().contentType(APPLICATION_JSON).body(fromValue(genre)))
                .switchIfEmpty(notFound().build());
    }

    @NonNull
    public Mono<ServerResponse> create(ServerRequest req) {
        return validate(req, Genre.class, tuple ->
                ok().contentType(APPLICATION_JSON).body(genreRepository.save(tuple.getBody()), Genre.class)
        );
    }

    @NonNull
    public Mono<ServerResponse> update(ServerRequest req) {
        return validate(req, Genre.class, tuple -> Mono
                .just(tuple.getBody())
                .zipWith(genreRepository.findById(tuple.getReq().pathVariable("id")),
                        (newGenre, oldGenre) -> {
                            Genre merged = new Genre();
                            merged.setId(oldGenre.getId());
                            merged.setName(newGenre.getName());
                            return merged;
                        }
                )
                .flatMap(genre ->
                        ok().contentType(APPLICATION_JSON).body(genreRepository.save(genre), Genre.class)
                )
                .switchIfEmpty(notFound().build())
        );
    }

    @NonNull
    public Mono<ServerResponse> delete(ServerRequest req) {
        return genreRepository
                .findById(req.pathVariable("id"))
                .flatMap(genre -> noContent().build(genreRepository
                        .deleteById(genre.getId())
                        .thenEmpty(bookRepository
                                .getBooksByGenreId(genre.getId())
                                .flatMap(book -> bookRepository
                                        .delete(book)
                                        .thenEmpty(commentRepository
                                                .getCommentsByBookId(book.getId())
                                                .flatMap(commentRepository::delete)
                                        )
                                )
                        )
                ))
                .switchIfEmpty(notFound().build());
    }
}
