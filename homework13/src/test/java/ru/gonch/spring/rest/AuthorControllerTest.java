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
import ru.gonch.spring.service.AuthorService;
import ru.gonch.spring.service.UserServiceImpl;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthorController.class)
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
class AuthorControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private UserServiceImpl userService;

    @Test
    void validateNameOnAddTest() throws Exception {
        mvc.perform(post("/author/add").param("id", "1").param("name", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<div class=\"error\">")));
    }

    @Test
    void validateNameOnEditTest() throws Exception {
        Author author = new Author();
        author.setId(1L);
        author.setName("author by id");
        when(authorService.getById(1L)).thenReturn(Optional.of(author));

        mvc.perform(post("/author/edit").queryParam("id", "1").param("id", "1").param("name", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<div class=\"error\">")));
    }

    @Test
    void emptyListPageTest() throws Exception {
        mvc.perform(get("/author"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("No authors")));
    }

    @Test
    void notFoundPageTest() throws Exception {
        when(authorService.getById(1L)).thenThrow(IllegalArgumentException.class);

        mvc.perform(get("/author/edit").queryParam("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Object not found")));
    }

    @Test
    void errorPageTest() throws Exception {
        when(authorService.deleteById(1L)).thenThrow(RuntimeException.class);

        mvc.perform(get("/author/delete").queryParam("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Unexpected error")));
    }
}
