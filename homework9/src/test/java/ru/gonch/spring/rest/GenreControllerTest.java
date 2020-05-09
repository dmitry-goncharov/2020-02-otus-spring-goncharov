package ru.gonch.spring.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.gonch.spring.model.Genre;
import ru.gonch.spring.service.GenreService;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GenreController.class)
class GenreControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private GenreService genreService;

    @Test
    void validateNameOnAddTest() throws Exception {
        mvc.perform(post("/genre/add").param("id", "1").param("name", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<div class=\"error\">")));
    }

    @Test
    void validateNameOnEditTest() throws Exception {
        when(genreService.getById(1L)).thenReturn(Optional.of(new Genre(1L, "genre by id")));

        mvc.perform(post("/genre/edit").queryParam("id", "1").param("id", "1").param("name", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<div class=\"error\">")));
    }

    @Test
    void emptyListPageTest() throws Exception {
        mvc.perform(get("/genre"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("No genres")));
    }

    @Test
    void notFoundPageTest() throws Exception {
        when(genreService.getById(1L)).thenThrow(IllegalArgumentException.class);

        mvc.perform(get("/genre/edit").queryParam("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Object not found")));
    }

    @Test
    void errorPageTest() throws Exception {
        when(genreService.deleteById(1L)).thenThrow(RuntimeException.class);

        mvc.perform(get("/genre/delete").queryParam("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Unexpected error")));
    }
}
