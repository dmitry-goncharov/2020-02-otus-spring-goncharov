package ru.gonch.spring.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.gonch.spring.model.Genre;
import ru.gonch.spring.service.GenreService;

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
@WebMvcTest(GenreController.class)
class GenreControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private GenreService genreService;

    @Test
    void validateNameOnAddTest() throws Exception {
        String json = "{\"name\":\"\"}";

        mvc.perform(post("/api/genre").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("name")));
    }

    @Test
    void validateNameOnEditTest() throws Exception {
        String json = "{\"name\":\"\"}";
        Genre newGenre = new Genre();
        newGenre.setId(1L);
        newGenre.setName("genre");

        when(genreService.getById(1L)).thenReturn(Optional.of(newGenre));

        mvc.perform(put("/api/genre/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("name")));
    }

    @Test
    void getEmptyListTest() throws Exception {
        mvc.perform(get("/api/genre"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getNotFoundTest() throws Exception {
        when(genreService.getById(1L)).thenThrow(IllegalArgumentException.class);

        mvc.perform(get("/api/genre/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Object not found")));
    }

    @Test
    void getServerErrorTest() throws Exception {
        when(genreService.deleteById(1L)).thenThrow(RuntimeException.class);

        mvc.perform(delete("/api/genre/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Unexpected error")));
    }
}
