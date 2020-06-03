package ru.gonch.spring.handler;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gonch.spring.model.Book;
import ru.gonch.spring.repository.BookRepository;
import ru.gonch.spring.repository.CommentRepository;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class BookHandler extends ValidationHandler<Book> {
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    public BookHandler(Validator validator,
                       BookRepository bookRepository,
                       CommentRepository commentRepository) {
        super(validator);
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    @NonNull
    public Mono<ServerResponse> list(ServerRequest req) {
        Optional<String> genreIdOpt = req.queryParam("genreId");
        Optional<String> authorIdOpt = req.queryParam("authorId");
        Flux<Book> bookFlux = genreIdOpt
                .map(genreId -> authorIdOpt
                        .map(authorId -> bookRepository.getBooksByGenreId(genreId).filter(book -> book.getAuthorId().equals(authorId)))
                        .orElseGet(() -> bookRepository.getBooksByGenreId(genreId))
                )
                .orElseGet(() -> authorIdOpt
                        .map(bookRepository::getBooksByAuthorId)
                        .orElseGet(bookRepository::findAll)
                );
        return ok().contentType(APPLICATION_JSON).body(bookFlux, Book.class);
    }

    @NonNull
    public Mono<ServerResponse> one(ServerRequest req) {
        return bookRepository
                .findById(req.pathVariable("id"))
                .flatMap(book -> ok().contentType(APPLICATION_JSON).body(fromValue(book)))
                .switchIfEmpty(notFound().build());
    }

    @NonNull
    public Mono<ServerResponse> create(ServerRequest req) {
        return validate(req, Book.class, tuple ->
                ok().contentType(APPLICATION_JSON).body(bookRepository.save(tuple.getBody()), Book.class)
        );
    }

    @NonNull
    public Mono<ServerResponse> update(ServerRequest req) {
        return validate(req, Book.class, tuple -> Mono
                .just(tuple.getBody())
                .zipWith(bookRepository.findById(req.pathVariable("id")),
                        (book, existingBook) -> {
                            Book merged = new Book();
                            merged.setId(existingBook.getId());
                            merged.setName(book.getName());
                            merged.setGenreId(book.getGenreId());
                            merged.setAuthorId(book.getAuthorId());
                            return merged;
                        }
                )
                .flatMap(book ->
                        ok().contentType(APPLICATION_JSON).body(bookRepository.save(book), Book.class)
                )
                .switchIfEmpty(notFound().build())
        );
    }

    @NonNull
    public Mono<ServerResponse> delete(ServerRequest req) {
        return bookRepository
                .findById(req.pathVariable("id"))
                .flatMap(book -> noContent().build(bookRepository
                        .deleteById(book.getId())
                        .thenEmpty(commentRepository
                                .getCommentsByBookId(book.getId())
                                .flatMap(commentRepository::delete)
                        )
                ))
                .switchIfEmpty(notFound().build());
    }
}
