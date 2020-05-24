package ru.gonch.spring.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.gonch.spring.model.Comment;
import ru.gonch.spring.service.CommentService;

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
@WebMvcTest(CommentController.class)
class CommentControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private CommentService commentService;

    @Test
    void getBooksByGenreIdTest() throws Exception {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setName("name by bookId");
        comment.setText("text");
        comment.setBookId(1L);

        when(commentService.getCommentsByBookId(1L)).thenReturn(List.of(comment));

        mvc.perform(get("/api/comment").queryParam("bookId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("name by bookId")));
    }

    @Test
    void validateNameOnAddTest() throws Exception {
        String json = "{\"name\":\"\", \"text\":\"text\", \"bookId\":\"1\"}";

        mvc.perform(post("/api/comment").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("name")));
    }

    @Test
    void validateTextOnAddTest() throws Exception {
        String json = "{\"name\":\"name\", \"text\":\"\", \"bookId\":\"1\"}";

        mvc.perform(post("/api/comment").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("text")));
    }

    @Test
    void validateBookIdOnAddTest() throws Exception {
        String json = "{\"name\":\"name\", \"text\":\"text\"}";

        mvc.perform(post("/api/comment").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("bookId")));
    }

    @Test
    void validateNameOnEditTest() throws Exception {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setName("from");
        comment.setText("text");
        comment.setBookId(1L);
        String json = "{\"name\":\"\", \"text\":\"text\", \"bookId\":\"1\"}";

        when(commentService.getById(1L)).thenReturn(Optional.of(comment));

        mvc.perform(put("/api/comment/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("name")));
    }

    @Test
    void validateTextOnEditTest() throws Exception {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setName("from");
        comment.setText("text");
        comment.setBookId(1L);
        String json = "{\"name\":\"name\", \"text\":\"\", \"bookId\":\"1\"}";

        when(commentService.getById(1L)).thenReturn(Optional.of(comment));

        mvc.perform(put("/api/comment/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("text")));
    }

    @Test
    void validateBookIdOnEditTest() throws Exception {
        String json = "{\"name\":\"name\", \"text\":\"text\"}";

        mvc.perform(put("/api/comment/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("bookId")));
    }

    @Test
    void getEmptyListTest() throws Exception {
        mvc.perform(get("/api/comment"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));
    }

    @Test
    void getNotFoundTest() throws Exception {
        when(commentService.getById(1L)).thenThrow(IllegalArgumentException.class);

        mvc.perform(get("/api/comment/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Object not found")));
    }

    @Test
    void getServerErrorTest() throws Exception {
        when(commentService.deleteById(1L)).thenThrow(RuntimeException.class);

        mvc.perform(delete("/api/comment/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Unexpected error")));
    }
}
