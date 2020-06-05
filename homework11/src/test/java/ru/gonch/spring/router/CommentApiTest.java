package ru.gonch.spring.router;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Mono;
import ru.gonch.spring.model.Comment;
import ru.gonch.spring.repository.CommentRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentApiTest {
    @Autowired
    private RouterFunction route;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void getListTest() {
        buildClient().get()
                .uri("/api/comment")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void getNotFoundTest() {
        buildClient().get()
                .uri("/api/comment/1")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void getOkTest() {
        Comment comment = commentRepository.findAll().blockFirst();
        assertNotNull(comment);
        buildClient().get()
                .uri("/api/comment/" + comment.getId())
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void postBadRequestTest() {
        Comment comment = new Comment();
        buildClient().post()
                .uri("/api/comment")
                .body(Mono.just(comment), Comment.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void postOkTest() {
        Comment comment = new Comment();
        comment.setName("qqq");
        comment.setText("www");
        comment.setBookId("1");
        buildClient().post()
                .uri("/api/comment")
                .body(Mono.just(comment), Comment.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void putBadRequestTest() {
        Comment comment = new Comment();
        buildClient().put()
                .uri("/api/comment/1")
                .body(Mono.just(comment), Comment.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }


    @Test
    public void putNotFoundTest() {
        Comment comment = new Comment();
        comment.setId("1");
        comment.setName("qqq");
        comment.setText("www");
        comment.setBookId("1");
        buildClient().put()
                .uri("/api/comment/1")
                .body(Mono.just(comment), Comment.class)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void putOkTest() {
        Comment comment = commentRepository.findAll().blockFirst();
        assertNotNull(comment);
        comment.setName("qqq");
        comment.setText("www");
        comment.setBookId("1");
        buildClient().put()
                .uri("/api/comment/" + comment.getId())
                .body(Mono.just(comment), Comment.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void deleteNotFoundTest() {
        buildClient().delete()
                .uri("/api/comment/1")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void deleteOkTest() {
        Comment comment = commentRepository.findAll().blockLast();
        assertNotNull(comment);
        buildClient().delete()
                .uri("/api/comment/" + comment.getId())
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    private WebTestClient buildClient() {
        return WebTestClient.bindToRouterFunction(route).build();
    }
}
