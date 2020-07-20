package ru.gonch.spring.rest;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.gonch.spring.model.Genre;
import ru.gonch.spring.service.GenreService;
import ru.gonch.spring.service.UserServiceImpl;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GenreController.class)
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
class GenreControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private GenreService genreService;
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private MeterRegistry meterRegistry;

    @Test
    void validateNameOnAddTest() throws Exception {
        mvc.perform(post("/genre/add").param("id", "1").param("name", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<div class=\"error\">")));
    }

    @Test
    void validateNameOnEditTest() throws Exception {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("genre by id");

        when(genreService.getById(1L)).thenReturn(Optional.of(genre));

        mvc.perform(post("/genre/edit").queryParam("id", "1").param("id", "1").param("name", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<div class=\"error\">")));
    }

    @Test
    void emptyListPageTest() throws Exception {
        Counter counter = mock(Counter.class);
        when(meterRegistry.counter(anyString())).thenReturn(counter);

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
