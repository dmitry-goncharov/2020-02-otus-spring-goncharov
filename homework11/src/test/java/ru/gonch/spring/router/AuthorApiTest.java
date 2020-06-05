package ru.gonch.spring.router;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Mono;
import ru.gonch.spring.model.Author;
import ru.gonch.spring.repository.AuthorRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorApiTest {
    @Autowired
    private RouterFunction route;
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void getListTest() {
        buildClient().get()
                .uri("/api/author")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void getNotFoundTest() {
        buildClient().get()
                .uri("/api/author/1")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void getOkTest() {
        Author author = authorRepository.findAll().blockFirst();
        assertNotNull(author);
        buildClient().get()
                .uri("/api/author/" + author.getId())
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void postBadRequestTest() {
        Author author = new Author();
        buildClient().post()
                .uri("/api/author")
                .body(Mono.just(author), Author.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void postOkTest() {
        Author author = new Author();
        author.setName("qqq");
        buildClient().post()
                .uri("/api/author")
                .body(Mono.just(author), Author.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void putBadRequestTest() {
        Author author = new Author();
        buildClient().put()
                .uri("/api/author/1")
                .body(Mono.just(author), Author.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }


    @Test
    public void putNotFoundTest() {
        Author author = new Author();
        author.setId("1");
        author.setName("qqq");
        buildClient().put()
                .uri("/api/author/1")
                .body(Mono.just(author), Author.class)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void putOkTest() {
        Author author = authorRepository.findAll().blockFirst();
        assertNotNull(author);
        author.setName("qqq");
        buildClient().put()
                .uri("/api/author/" + author.getId())
                .body(Mono.just(author), Author.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void deleteNotFoundTest() {
        buildClient().delete()
                .uri("/api/author/1")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void deleteOkTest() {
        Author author = authorRepository.findAll().blockLast();
        assertNotNull(author);
        buildClient().delete()
                .uri("/api/author/" + author.getId())
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    private WebTestClient buildClient() {
        return WebTestClient.bindToRouterFunction(route).build();
    }
}
