package ru.gonch.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.gonch.spring.model.Book;
import ru.gonch.spring.service.BookService;

import java.util.List;
import java.util.Optional;

@ShellComponent
public class BookShell {
    private final BookService bookService;

    public BookShell(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(value = "Add book", key = {"add-book"})
    public String addBook(@ShellOption String name,
                          @ShellOption String genreId,
                          @ShellOption String authorId) {
        try {
            String id = bookService.save(new Book(name, genreId, authorId));
            return String.format("Book with id %s has been added", id);
        } catch (IllegalArgumentException e) {
            return "Bad request: " + e.getMessage();
        }
    }

    @ShellMethod(value = "Get book by id", key = {"get-book"})
    public String getBook(@ShellOption String id) {
        Optional<Book> book = bookService.getById(id);
        return String.format("Book: %s", book.isPresent() ? book.get() : "none");
    }

    @ShellMethod(value = "Get all books", key = {"get-books"})
    public String getBooks() {
        List<Book> books = bookService.getAll();
        return String.format("Books: %s", books);
    }

    @ShellMethod(value = "Get all books by genre id", key = {"get-books-by-genre"})
    public String getBooksByGenre(@ShellOption String genreId) {
        List<Book> books = bookService.getBooksByGenreId(genreId);
        return String.format("Books: %s", books);
    }

    @ShellMethod(value = "Get all books by author id", key = {"get-books-by-author"})
    public String getBooksByAuthor(@ShellOption String authorId) {
        List<Book> books = bookService.getBooksByAuthorId(authorId);
        return String.format("Books: %s", books);
    }

    @ShellMethod(value = "Update book by id", key = {"upd-book"})
    public String updateBook(@ShellOption String id,
                             @ShellOption String name,
                             @ShellOption String genreId,
                             @ShellOption String authorId) {
        try {
            if (bookService.update(new Book(id, name, genreId, authorId))) {
                return String.format("Book with id %s has been updated", id);
            } else {
                return getNotFoundString(id);
            }
        } catch (IllegalArgumentException e) {
            return "Bad request: " + e.getMessage();
        }
    }

    @ShellMethod(value = "Delete book command", key = {"del-book"})
    public String deleteBook(@ShellOption String id) {
        if (bookService.deleteById(id)) {
            return String.format("Book with id %s has been deleted", id);
        } else {
            return getNotFoundString(id);
        }
    }

    private String getNotFoundString(String id) {
        return String.format("Book with id %s not found", id);
    }
}
