package ru.gonch.spring.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.gonch.spring.handler.AuthorHandler;
import ru.gonch.spring.handler.BookHandler;
import ru.gonch.spring.handler.CommentHandler;
import ru.gonch.spring.handler.GenreHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AppRouter {
    @Bean
    public RouterFunction<ServerResponse> buildRoutes(GenreHandler genreHandler,
                                                      AuthorHandler authorHandler,
                                                      BookHandler bookHandler,
                                                      CommentHandler commentHandler) {
        return route()
                // genre api
                .GET("/api/genre", accept(APPLICATION_JSON), genreHandler::list)
                .GET("/api/genre/{id}", accept(APPLICATION_JSON), genreHandler::one)
                .POST("/api/genre", contentType(APPLICATION_JSON), genreHandler::create)
                .PUT("/api/genre/{id}", contentType(APPLICATION_JSON), genreHandler::update)
                .DELETE("/api/genre/{id}", accept(APPLICATION_JSON), genreHandler::delete)
                // author api
                .GET("/api/author", accept(APPLICATION_JSON), authorHandler::list)
                .GET("/api/author/{id}", accept(APPLICATION_JSON), authorHandler::one)
                .POST("/api/author", contentType(APPLICATION_JSON), authorHandler::create)
                .PUT("/api/author/{id}", contentType(APPLICATION_JSON), authorHandler::update)
                .DELETE("/api/author/{id}", accept(APPLICATION_JSON), authorHandler::delete)
                // book api
                .GET("/api/book", accept(APPLICATION_JSON), bookHandler::list)
                .GET("/api/book/{id}", accept(APPLICATION_JSON), bookHandler::one)
                .POST("/api/book", contentType(APPLICATION_JSON), bookHandler::create)
                .PUT("/api/book/{id}", contentType(APPLICATION_JSON), bookHandler::update)
                .DELETE("/api/book/{id}", accept(APPLICATION_JSON), bookHandler::delete)
                // comment api
                .GET("/api/comment", accept(APPLICATION_JSON), commentHandler::list)
                .GET("/api/comment/{id}", accept(APPLICATION_JSON), commentHandler::one)
                .POST("/api/comment", contentType(APPLICATION_JSON), commentHandler::create)
                .PUT("/api/comment/{id}", contentType(APPLICATION_JSON), commentHandler::update)
                .DELETE("/api/comment/{id}", accept(APPLICATION_JSON), commentHandler::delete)
                .build();
    }
}
