package ru.gonch.spring.handler;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gonch.spring.model.Comment;
import ru.gonch.spring.repository.CommentRepository;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class CommentHandler extends ValidationHandler<Comment> {
    private final CommentRepository commentRepository;

    public CommentHandler(Validator validator,
                          CommentRepository commentRepository) {
        super(validator);
        this.commentRepository = commentRepository;
    }

    @NonNull
    public Mono<ServerResponse> list(ServerRequest req) {
        Optional<String> bookIdOpt = req.queryParam("bookId");
        Flux<Comment> commentFlux = bookIdOpt
                .map(commentRepository::getCommentsByBookId)
                .orElseGet(commentRepository::findAll);
        return ok().contentType(APPLICATION_JSON).body(commentFlux, Comment.class);
    }

    @NonNull
    public Mono<ServerResponse> one(ServerRequest req) {
        return commentRepository
                .findById(req.pathVariable("id"))
                .flatMap(comment -> ok().contentType(APPLICATION_JSON).body(fromValue(comment)))
                .switchIfEmpty(notFound().build());
    }

    @NonNull
    public Mono<ServerResponse> create(ServerRequest req) {
        return validate(req, Comment.class, tuple ->
                ok().contentType(APPLICATION_JSON).body(commentRepository.save(tuple.getBody()), Comment.class)
        );
    }

    @NonNull
    public Mono<ServerResponse> update(ServerRequest req) {
        return validate(req, Comment.class, tuple -> Mono
                .just(tuple.getBody())
                .zipWith(commentRepository.findById(req.pathVariable("id")),
                        (comment, existingComment) -> {
                            Comment merged = new Comment();
                            merged.setId(existingComment.getId());
                            merged.setName(comment.getName());
                            merged.setText(comment.getText());
                            merged.setBookId(comment.getBookId());
                            return merged;
                        }
                )
                .flatMap(comment ->
                        ok().contentType(APPLICATION_JSON).body(commentRepository.save(comment), Comment.class)
                )
                .switchIfEmpty(notFound().build())
        );
    }

    @NonNull
    public Mono<ServerResponse> delete(ServerRequest req) {
        return commentRepository
                .findById(req.pathVariable("id"))
                .flatMap(comment -> noContent().build(commentRepository.deleteById(comment.getId())))
                .switchIfEmpty(notFound().build());
    }
}
