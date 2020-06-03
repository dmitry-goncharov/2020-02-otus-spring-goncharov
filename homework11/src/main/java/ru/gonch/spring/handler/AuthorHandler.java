package ru.gonch.spring.handler;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.gonch.spring.model.Author;
import ru.gonch.spring.repository.AuthorRepository;
import ru.gonch.spring.repository.BookRepository;
import ru.gonch.spring.repository.CommentRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class AuthorHandler extends ValidationHandler<Author> {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    public AuthorHandler(Validator validator,
                         AuthorRepository authorRepository,
                         BookRepository bookRepository,
                         CommentRepository commentRepository) {
        super(validator);
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    @NonNull
    public Mono<ServerResponse> list(ServerRequest req) {
        return ok().contentType(APPLICATION_JSON).body(authorRepository.findAll(), Author.class);
    }

    @NonNull
    public Mono<ServerResponse> one(ServerRequest req) {
        return authorRepository
                .findById(req.pathVariable("id"))
                .flatMap(author -> ok().contentType(APPLICATION_JSON).body(fromValue(author)))
                .switchIfEmpty(notFound().build());
    }

    @NonNull
    public Mono<ServerResponse> create(ServerRequest req) {
        return validate(req, Author.class, tuple ->
                ok().contentType(APPLICATION_JSON).body(authorRepository.save(tuple.getBody()), Author.class)
        );
    }

    @NonNull
    public Mono<ServerResponse> update(ServerRequest req) {
        return validate(req, Author.class, tuple -> Mono
                .just(tuple.getBody())
                .zipWith(authorRepository.findById(req.pathVariable("id")),
                        (author, existingAuthor) -> {
                            Author merged = new Author();
                            merged.setId(existingAuthor.getId());
                            merged.setName(author.getName());
                            return merged;
                        }
                )
                .flatMap(author ->
                        ok().contentType(APPLICATION_JSON).body(authorRepository.save(author), Author.class)
                )
                .switchIfEmpty(notFound().build())
        );
    }

    @NonNull
    public Mono<ServerResponse> delete(ServerRequest req) {
        return authorRepository
                .findById(req.pathVariable("id"))
                .flatMap(author -> noContent().build(authorRepository
                        .deleteById(author.getId())
                        .thenEmpty(bookRepository
                                .getBooksByAuthorId(author.getId())
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
