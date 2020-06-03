package ru.gonch.spring.router;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Mono;
import ru.gonch.spring.model.Book;
import ru.gonch.spring.repository.BookRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookApiTest {
    @Autowired
    private RouterFunction route;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void getListTest() {
        buildClient().get()
                .uri("/api/book")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void getNotFoundTest() {
        buildClient().get()
                .uri("/api/book/1")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void getOkTest() {
        Book book = bookRepository.findAll().blockFirst();
        assertNotNull(book);
        buildClient().get()
                .uri("/api/book/" + book.getId())
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void postBadRequestTest() {
        Book book = new Book();
        buildClient().post()
                .uri("/api/book")
                .body(Mono.just(book), Book.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void postOkTest() {
        Book book = new Book();
        book.setName("qqq");
        book.setGenreId("1");
        book.setAuthorId("1");
        buildClient().post()
                .uri("/api/book")
                .body(Mono.just(book), Book.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void putBadRequestTest() {
        Book book = new Book();
        buildClient().put()
                .uri("/api/book/1")
                .body(Mono.just(book), Book.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }


    @Test
    public void putNotFoundTest() {
        Book book = new Book();
        book.setId("1");
        book.setName("qqq");
        book.setGenreId("1");
        book.setAuthorId("1");
        buildClient().put()
                .uri("/api/book/1")
                .body(Mono.just(book), Book.class)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void putOkTest() {
        Book book = bookRepository.findAll().blockFirst();
        assertNotNull(book);
        book.setName("qqq");
        book.setGenreId("1");
        book.setAuthorId("1");
        buildClient().put()
                .uri("/api/book/" + book.getId())
                .body(Mono.just(book), Book.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void deleteNotFoundTest() {
        buildClient().delete()
                .uri("/api/book/1")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void deleteOkTest() {
        Book book = bookRepository.findAll().blockLast();
        assertNotNull(book);
        buildClient().delete()
                .uri("/api/book/" + book.getId())
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    private WebTestClient buildClient() {
        return WebTestClient.bindToRouterFunction(route).build();
    }
}
