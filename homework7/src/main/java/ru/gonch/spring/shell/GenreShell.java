package ru.gonch.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.gonch.spring.model.Genre;
import ru.gonch.spring.service.GenreService;

import java.util.List;
import java.util.Optional;

@ShellComponent
public class GenreShell {
    private final GenreService genreService;

    public GenreShell(GenreService genreService) {
        this.genreService = genreService;
    }

    @ShellMethod(value = "Add genre", key = {"add-genre"})
    public String addGenre(@ShellOption String name) {
        long id = genreService.save(new Genre(name));
        return String.format("Genre with id %d has been added", id);
    }

    @ShellMethod(value = "Get genre by id", key = {"get-genre"})
    public String getGenre(@ShellOption long id) {
        Optional<Genre> genre = genreService.getById(id);
        return String.format("Genre: %s", genre.isPresent() ? genre.get() : "none");
    }

    @ShellMethod(value = "Get all genres", key = {"get-genres"})
    public String getGenres() {
        List<Genre> genres = genreService.getAll();
        return String.format("Genres: %s", genres);
    }

    @ShellMethod(value = "Update genre by id", key = {"upd-genre"})
    public String updateGenre(@ShellOption long id,
                              @ShellOption String name) {
        if (genreService.update(new Genre(id, name))) {
            return String.format("Genre with id %d has been updated", id);
        } else {
            return getNotFoundString(id);
        }
    }

    @ShellMethod(value = "Delete genre command", key = {"del-genre"})
    public String deleteGenre(@ShellOption long id) {
        if (genreService.deleteById(id)) {
            return String.format("Genre with id %d has been deleted", id);
        } else {
            return getNotFoundString(id);
        }
    }

    private String getNotFoundString(long id) {
        return String.format("Genre with id %d not found", id);
    }
}
