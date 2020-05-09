package ru.gonch.spring.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.gonch.spring.model.Author;
import ru.gonch.spring.model.Book;
import ru.gonch.spring.model.Genre;
import ru.gonch.spring.service.AuthorService;
import ru.gonch.spring.service.BookService;
import ru.gonch.spring.service.GenreService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookService bookService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private AuthorService authorService;

    @Test
    void getBooksByGenreIdTest() throws Exception {
        when(bookService.getBooksByGenreId(1L)).thenReturn(List.of(new Book(1L, "book by genreID", 1L, 1L)));
        when(genreService.getById(1L)).thenReturn(Optional.of(new Genre(1L, "genre by genreID")));

        mvc.perform(get("/book").queryParam("genreID", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("book by genreID")));
    }

    @Test
    void getBooksByAuthorIdTest() throws Exception {
        when(bookService.getBooksByAuthorId(1L)).thenReturn(List.of(new Book(1L, "book by authorID", 1L, 1L)));
        when(authorService.getById(1L)).thenReturn(Optional.of(new Author(1L, "author by authorID")));

        mvc.perform(get("/book").queryParam("authorID", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("book by authorID")));
    }

    @Test
    void validateNameOnAddTest() throws Exception {
        mvc.perform(post("/book/add").param("id", "1").param("name", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<div class=\"error\">")));
    }

    @Test
    void validateNameOnEditTest() throws Exception {
        when(bookService.getById(1L)).thenReturn(Optional.of(new Book(1L, "book by authorID", 1L, 1L)));

        mvc.perform(post("/book/edit").queryParam("bookID", "1").param("id", "1").param("name", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<div class=\"error\">")));
    }

    @Test
    void emptyListPageTest() throws Exception {
        mvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("No books")));
    }

    @Test
    void notFoundPageTest() throws Exception {
        when(bookService.getById(1L)).thenThrow(IllegalArgumentException.class);

        mvc.perform(get("/book/edit").queryParam("bookID", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Object not found")));
    }

    @Test
    void errorPageTest() throws Exception {
        when(bookService.deleteById(1L)).thenThrow(RuntimeException.class);

        mvc.perform(get("/book/delete").queryParam("bookID", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Unexpected error")));
    }
}
