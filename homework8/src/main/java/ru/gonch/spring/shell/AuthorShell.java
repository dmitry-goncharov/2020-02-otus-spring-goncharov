package ru.gonch.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.gonch.spring.model.Author;
import ru.gonch.spring.service.AuthorService;

import java.util.List;
import java.util.Optional;

@ShellComponent
public class AuthorShell {
    private final AuthorService authorService;

    public AuthorShell(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ShellMethod(value = "Add author", key = {"add-author"})
    public String addAuthor(@ShellOption String name) {
        String id = authorService.save(new Author(name));
        return String.format("Author with id %s has been added", id);
    }

    @ShellMethod(value = "Get author by id", key = {"get-author"})
    public String getAuthor(@ShellOption String id) {
        Optional<Author> author = authorService.getById(id);
        return String.format("Author: %s", author.isPresent() ? author.get() : "none");
    }

    @ShellMethod(value = "Get all authors", key = {"get-authors"})
    public String getAuthors() {
        List<Author> authors = authorService.getAll();
        return String.format("Authors: %s", authors);
    }

    @ShellMethod(value = "Update author by id", key = {"upd-author"})
    public String updateAuthor(@ShellOption String id,
                               @ShellOption String name) {
        if (authorService.update(new Author(id, name))) {
            return String.format("Author with id %s has been updated", id);
        } else {
            return getNotFoundString(id);
        }
    }

    @ShellMethod(value = "Delete author command", key = {"del-author"})
    public String deleteAuthor(@ShellOption String id) {
        if (authorService.deleteById(id)) {
            return String.format("Author with id %s has been deleted", id);
        } else {
            return getNotFoundString(id);
        }
    }

    private String getNotFoundString(String id) {
        return String.format("Author with id %s not found", id);
    }
}
