package ru.gonch.spring.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.gonch.spring.model.Author;
import ru.gonch.spring.service.AuthorService;

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
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthorService authorService;

    @Test
    void validateNameOnAddTest() throws Exception {
        String json = "{\"name\":\"\"}";

        mvc.perform(post("/api/author").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("name")));
    }

    @Test
    void validateNameOnEditTest() throws Exception {
        String json = "{\"name\":\"\"}";
        Author author = new Author();
        author.setId(1L);
        author.setName("author");

        when(authorService.getById(1L)).thenReturn(Optional.of(author));

        mvc.perform(put("/api/author/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("name")));
    }

    @Test
    void getEmptyListTest() throws Exception {
        mvc.perform(get("/api/author"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getNotFoundTest() throws Exception {
        when(authorService.getById(1L)).thenThrow(IllegalArgumentException.class);

        mvc.perform(get("/api/author/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Object not found")));
    }

    @Test
    void getServerErrorTest() throws Exception {
        when(authorService.deleteById(1L)).thenThrow(RuntimeException.class);

        mvc.perform(delete("/api/author/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Unexpected error")));
    }
}
