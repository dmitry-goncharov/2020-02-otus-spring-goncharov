package ru.gonch.spring.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.gonch.spring.model.Author;
import ru.gonch.spring.model.Book;
import ru.gonch.spring.model.Genre;
import ru.gonch.spring.service.AuthorService;
import ru.gonch.spring.service.BookService;
import ru.gonch.spring.service.GenreService;
import ru.gonch.spring.service.UserService;

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
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
class BookControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookService bookService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private UserService userService;

    @Test
    void getBooksByGenreIdTest() throws Exception {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("genre by genreID");

        Book book = new Book();
        book.setId(1L);
        book.setName("book by genreID");
        book.setGenreId(1L);
        book.setAuthorId(1L);

        when(bookService.getBooksByGenreId(1L)).thenReturn(List.of(book));
        when(genreService.getById(1L)).thenReturn(Optional.of(genre));

        mvc.perform(get("/book").queryParam("genreID", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("book by genreID")));
    }

    @Test
    void getBooksByAuthorIdTest() throws Exception {
        Author author = new Author();
        author.setId(1L);
        author.setName("author by authorID");

        Book book = new Book();
        book.setId(1L);
        book.setName("book by authorID");
        book.setGenreId(1L);
        book.setAuthorId(1L);

        when(bookService.getBooksByAuthorId(1L)).thenReturn(List.of(book));
        when(authorService.getById(1L)).thenReturn(Optional.of(author));

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
        Book book = new Book();
        book.setId(1L);
        book.setName("book by authorID");
        book.setGenreId(1L);
        book.setAuthorId(1L);

        when(bookService.getById(1L)).thenReturn(Optional.of(book));

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
