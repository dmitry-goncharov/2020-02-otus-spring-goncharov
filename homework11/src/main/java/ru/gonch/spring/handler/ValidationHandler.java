package ru.gonch.spring.handler;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.gonch.spring.model.Error;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;

abstract class ValidationHandler<T> {
    class Tuple {
        private T body;
        private ServerRequest req;

        Tuple(T body, ServerRequest req) {
            this.body = body;
            this.req = req;
        }

        T getBody() {
            return body;
        }

        ServerRequest getReq() {
            return req;
        }
    }

    private final Validator validator;

    ValidationHandler(Validator validator) {
        this.validator = validator;
    }

    Mono<ServerResponse> validate(ServerRequest req, Class<T> cls, Function<Tuple, Mono<ServerResponse>> function) {
        return req
                .bodyToMono(cls)
                .flatMap(body -> {
                    Errors errors = new BeanPropertyBindingResult(body, cls.getName());
                    validator.validate(body, errors);
                    if (errors.getAllErrors().isEmpty()) {
                        return function.apply(new Tuple(body, req));
                    } else {
                        return badRequestResp(errors);
                    }
                });
    }

    private Mono<ServerResponse> badRequestResp(Errors errors) {
        return badRequest().contentType(APPLICATION_JSON).bodyValue(toBadRequest(errors));
    }

    private Error toBadRequest(Errors errors) {
        List<String> fieldErrors = errors.getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .collect(Collectors.toList());
        Error error = new Error();
        error.setMessage("Illegal arguments");
        error.setDetails(fieldErrors);
        return error;
    }
}
