package ru.gonch.spring.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.gonch.spring.model.Book;
import ru.gonch.spring.service.BookService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookService bookService;

    @Test
    void getBooksByGenreIdTest() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setName("book by genreId");
        book.setGenreId(1L);
        book.setAuthorId(1L);

        when(bookService.getBooksByGenreId(1L)).thenReturn(List.of(book));

        mvc.perform(get("/api/book").queryParam("genreId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("book by genreId")));
    }

    @Test
    void getBooksByAuthorIdTest() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setName("book by authorId");
        book.setGenreId(1L);
        book.setAuthorId(1L);

        when(bookService.getBooksByAuthorId(1L)).thenReturn(List.of(book));

        mvc.perform(get("/api/book").queryParam("authorId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("book by authorId")));
    }

    @Test
    void validateNameOnAddTest() throws Exception {
        String json = "{\"name\":\"\", \"genreId\":\"1\", \"authorId\":\"1\"}";

        mvc.perform(post("/api/book").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("name")));
    }

    @Test
    void validateGenreIdOnAddTest() throws Exception {
        String json = "{\"name\":\"name\", \"authorId\":\"1\"}";

        mvc.perform(post("/api/book").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("genreId")));
    }

    @Test
    void validateAuthorIdOnAddTest() throws Exception {
        String json = "{\"name\":\"name\", \"genreId\":\"1\"}";

        mvc.perform(post("/api/book").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("authorId")));
    }

    @Test
    void validateNameOnEditTest() throws Exception {
        String json = "{\"name\":\"\", \"genreId\":\"1\", \"authorId\":\"1\"}";
        Book book = new Book();
        book.setId(1L);
        book.setName("book");
        book.setGenreId(1L);
        book.setAuthorId(1L);

        when(bookService.getById(1L)).thenReturn(Optional.of(book));

        mvc.perform(put("/api/book/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("name")));
    }

    @Test
    void validateGenreIdOnEditTest() throws Exception {
        String json = "{\"name\":\"name\", \"authorId\":\"1\"}";
        Book book = new Book();
        book.setId(1L);
        book.setName("book");
        book.setGenreId(1L);
        book.setAuthorId(1L);

        when(bookService.getById(1L)).thenReturn(Optional.of(book));

        mvc.perform(put("/api/book/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("genreId")));
    }

    @Test
    void validateAuthorIdOnEditTest() throws Exception {
        String json = "{\"name\":\"name\", \"genreId\":\"1\"}";
        Book book = new Book();
        book.setId(1L);
        book.setName("book");
        book.setGenreId(1L);
        book.setAuthorId(1L);

        when(bookService.getById(1L)).thenReturn(Optional.of(book));

        mvc.perform(put("/api/book/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("authorId")));
    }

    @Test
    void getEmptyListTest() throws Exception {
        mvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));
    }

    @Test
    void getNotFoundTest() throws Exception {
        when(bookService.getById(1L)).thenThrow(IllegalArgumentException.class);

        mvc.perform(get("/api/book/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Object not found")));
    }

    @Test
    void getServerErrorTest() throws Exception {
        when(bookService.deleteById(1L)).thenThrow(RuntimeException.class);

        mvc.perform(delete("/api/book/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Unexpected error")));
    }
}
