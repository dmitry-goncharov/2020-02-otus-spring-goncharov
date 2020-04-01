package ru.gonch.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.gonch.spring.model.Author;
import ru.gonch.spring.model.Book;
import ru.gonch.spring.model.Genre;
import ru.gonch.spring.service.AuthorService;
import ru.gonch.spring.service.BookService;
import ru.gonch.spring.service.GenreService;

import java.util.List;
import java.util.Optional;

@ShellComponent
public class BookShell {
    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    public BookShell(BookService bookService, GenreService genreService, AuthorService authorService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @ShellMethod(value = "Add book", key = {"add-book"})
    public String addBook(@ShellOption String name,
                          @ShellOption long genreId,
                          @ShellOption long authorId) {
        if (genreService.getById(genreId).isEmpty()) {
            return String.format("Genre with id %d not found", genreId);
        }
        if (authorService.getById(authorId).isEmpty()) {
            return String.format("Author with id %d not found", authorId);
        }
        long id = bookService.insert(new Book(name, new Genre(genreId), new Author(authorId)));
        return String.format("Book with id %d has been added", id);
    }

    @ShellMethod(value = "Get book by id", key = {"get-book"})
    public String getBook(@ShellOption long id) {
        Optional<Book> book = bookService.getById(id);
        return String.format("Book: %s", book.isPresent() ? book.get() : "none");
    }

    @ShellMethod(value = "Get all books", key = {"get-books"})
    public String getBooks(@ShellOption int limit,
                           @ShellOption int offset) {
        List<Book> books = bookService.getAll(limit, offset);
        return String.format("Books: %s", books);
    }

    @ShellMethod(value = "Get books by genre id", key = {"get-books-by-genre"})
    public String getBooksByGenreId(@ShellOption long genreId,
                                    @ShellOption int limit,
                                    @ShellOption int offset) {
        List<Book> books = bookService.getBooksByGenreId(genreId, limit, offset);
        return String.format("Books: %s", books);
    }

    @ShellMethod(value = "Get books by author id", key = {"get-books-by-author"})
    public String getBooksByAuthorId(@ShellOption long authorId,
                                     @ShellOption int limit,
                                     @ShellOption int offset) {
        List<Book> books = bookService.getBooksByAuthorId(authorId, limit, offset);
        return String.format("Books: %s", books);
    }

    @ShellMethod(value = "Update book by id", key = {"upd-book"})
    public String updateBook(@ShellOption long id,
                             @ShellOption String name,
                             @ShellOption long genreId,
                             @ShellOption long authorId) {
        if (genreService.getById(genreId).isEmpty()) {
            return String.format("Genre with id %d not found", genreId);
        }
        if (authorService.getById(authorId).isEmpty()) {
            return String.format("Author with id %d not found", authorId);
        }
        if (bookService.update(new Book(id, name, new Genre(genreId), new Author(authorId)))) {
            return String.format("Book with id %d has been updated", id);
        } else {
            return getNotFoundString(id);
        }
    }

    @ShellMethod(value = "Delete book command", key = {"del-book"})
    public String deleteBook(@ShellOption long id) {
        if (bookService.deleteById(id)) {
            return String.format("Book with id %d has been deleted", id);
        } else {
            return getNotFoundString(id);
        }
    }

    private String getNotFoundString(long id) {
        return String.format("Book with id %d not found", id);
    }
}
