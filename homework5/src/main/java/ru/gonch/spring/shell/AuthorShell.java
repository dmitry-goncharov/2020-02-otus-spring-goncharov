package ru.gonch.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.gonch.spring.model.Author;
import ru.gonch.spring.service.AuthorService;

import java.util.List;

@ShellComponent
public class AuthorShell {
    private final AuthorService authorService;

    public AuthorShell(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ShellMethod(value = "Add author", key = {"add-author"})
    public String addAuthor(@ShellOption String name) {
        long id = authorService.insert(new Author(name));
        return String.format("Author with id %d has been added", id);
    }

    @ShellMethod(value = "Get author by id", key = {"get-author"})
    public String getAuthor(@ShellOption long id) {
        Author author = authorService.getById(id);
        return String.format("Author: %s", author);
    }

    @ShellMethod(value = "Get all authors", key = {"get-authors"})
    public String getAuthors(@ShellOption int limit,
                             @ShellOption int offset) {
        List<Author> authors = authorService.getAll(limit, offset);
        return String.format("Authors: %s", authors);
    }

    @ShellMethod(value = "Update author by id", key = {"upd-author"})
    public String updateAuthor(@ShellOption long id,
                               @ShellOption String name) {
        authorService.update(new Author(id, name));
        return String.format("Author with id %d has been updated", id);
    }

    @ShellMethod(value = "Delete author command", key = {"del-author"})
    public String deleteAuthor(@ShellOption long id) {
        authorService.deleteById(id);
        return String.format("Author with id %d has been deleted", id);
    }
}
