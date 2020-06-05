package ru.gonch.spring.router;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Mono;
import ru.gonch.spring.model.Genre;
import ru.gonch.spring.repository.GenreRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenreApiTest {
    @Autowired
    private RouterFunction route;
    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void getListTest() {
        buildClient().get()
                .uri("/api/genre")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void getNotFoundTest() {
        buildClient().get()
                .uri("/api/genre/1")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void getOkTest() {
        Genre genre = genreRepository.findAll().blockFirst();
        assertNotNull(genre);
        buildClient().get()
                .uri("/api/genre/" + genre.getId())
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void postBadRequestTest() {
        Genre genre = new Genre();
        buildClient().post()
                .uri("/api/genre")
                .body(Mono.just(genre), Genre.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void postOkTest() {
        Genre genre = new Genre();
        genre.setName("qqq");
        buildClient().post()
                .uri("/api/genre")
                .body(Mono.just(genre), Genre.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void putBadRequestTest() {
        Genre genre = new Genre();
        buildClient().put()
                .uri("/api/genre/1")
                .body(Mono.just(genre), Genre.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }


    @Test
    public void putNotFoundTest() {
        Genre genre = new Genre();
        genre.setId("1");
        genre.setName("qqq");
        buildClient().put()
                .uri("/api/genre/1")
                .body(Mono.just(genre), Genre.class)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void putOkTest() {
        Genre genre = genreRepository.findAll().blockFirst();
        assertNotNull(genre);
        genre.setName("qqq");
        buildClient().put()
                .uri("/api/genre/" + genre.getId())
                .body(Mono.just(genre), Genre.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void deleteNotFoundTest() {
        buildClient().delete()
                .uri("/api/genre/1")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void deleteOkTest() {
        Genre genre = genreRepository.findAll().blockLast();
        assertNotNull(genre);
        buildClient().delete()
                .uri("/api/genre/" + genre.getId())
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    private WebTestClient buildClient() {
        return WebTestClient.bindToRouterFunction(route).build();
    }
}
