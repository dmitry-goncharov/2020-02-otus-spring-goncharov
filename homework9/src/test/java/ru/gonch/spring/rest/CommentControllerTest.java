package ru.gonch.spring.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.gonch.spring.model.Book;
import ru.gonch.spring.model.Comment;
import ru.gonch.spring.service.BookService;
import ru.gonch.spring.service.CommentService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CommentController.class)
class CommentControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private CommentService commentService;
    @MockBean
    private BookService bookService;

    @Test
    void getBooksByGenreIdTest() throws Exception {
        when(commentService.getCommentsByBookId(1L)).thenReturn(List.of(new Comment(1L, "name by BookID", "text", 1L)));
        when(bookService.getById(1L)).thenReturn(Optional.of(new Book(1L, "book by id", 1L, 1L)));

        mvc.perform(get("/comment").queryParam("bookID", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("name by BookID")));
    }

    @Test
    void validateNameOnAddTest() throws Exception {
        mvc.perform(post("/comment/add").param("id", "1").param("name", "").param("text", "text"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<div class=\"error\">")));
    }

    @Test
    void validateTextOnAddTest() throws Exception {
        mvc.perform(post("/comment/add").param("id", "1").param("name", "name").param("text", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<div class=\"error\">")));
    }

    @Test
    void validateNameOnEditTest() throws Exception {
        when(commentService.getById(1L)).thenReturn(Optional.of(new Comment(1L, "from", "comment", 1L)));

        mvc.perform(post("/comment/edit").queryParam("id", "1").param("id", "1").param("name", "").param("text", "text"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<div class=\"error\">")));
    }

    @Test
    void validateTextOnEditTest() throws Exception {
        when(commentService.getById(1L)).thenReturn(Optional.of(new Comment(1L, "from", "comment", 1L)));

        mvc.perform(post("/comment/edit").queryParam("id", "1").param("id", "1").param("name", "name").param("text", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<div class=\"error\">")));
    }

    @Test
    void emptyListPageTest() throws Exception {
        mvc.perform(get("/comment"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("No comments")));
    }

    @Test
    void notFoundPageTest() throws Exception {
        when(commentService.getById(1L)).thenThrow(IllegalArgumentException.class);

        mvc.perform(get("/comment/edit").queryParam("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Object not found")));
    }

    @Test
    void errorPageTest() throws Exception {
        when(commentService.deleteById(1L)).thenThrow(RuntimeException.class);

        mvc.perform(get("/comment/delete").queryParam("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Unexpected error")));
    }
}
