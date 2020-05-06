package ru.gonch.spring.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.gonch.spring.model.Book;
import ru.gonch.spring.service.AuthorService;
import ru.gonch.spring.service.BookService;
import ru.gonch.spring.service.GenreService;

import javax.validation.Valid;

@Controller
public class BookController {
    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    public BookController(BookService bookService, GenreService genreService, AuthorService authorService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @GetMapping("/book")
    public String listPage(BookParams bookParams, Model model) {
        return prepareListPage(bookParams, model);
    }

    @GetMapping("/book/add")
    public String addPage(BookParams bookParams, Book book, Model model) {
        return prepareAddPage(model);
    }

    @GetMapping("/book/edit")
    public String editPage(BookParams bookParams, Model model) {
        return prepareEditPage(bookParams, model, true);
    }

    @PostMapping("/book/add")
    public String addObject(BookParams bookParams, @Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return prepareAddPage(model);
        } else {
            bookService.save(book);
            return prepareListPage(bookParams, model);
        }
    }

    @PostMapping("/book/edit")
    public String editObject(BookParams bookParams, @Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return prepareEditPage(bookParams, model, false);
        } else {
            bookService.save(book);
            return prepareListPage(bookParams, model);
        }
    }

    @GetMapping("/book/delete")
    public String deleteObject(BookParams bookParams, Model model) {
        bookService.deleteById(bookParams.getBookID());
        return prepareListPage(bookParams, model);
    }

    private String prepareListPage(BookParams bookParams, Model model) {
        model.addAttribute("books", ControllerUtils.getBooks(bookService, bookParams));
        if (bookParams.getGenreID() != null) {
            model.addAttribute("genre", genreService.getById(bookParams.getGenreID()).orElseThrow(IllegalArgumentException::new));
        }
        if (bookParams.getAuthorID() != null) {
            model.addAttribute("author", authorService.getById(bookParams.getAuthorID()).orElseThrow(IllegalArgumentException::new));
        }
        return "book-list";
    }

    private String prepareAddPage(Model model) {
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("authors", authorService.getAll());
        return "book-add";
    }

    private String prepareEditPage(BookParams bookParams, Model model, boolean addObjectById) {
        if (addObjectById) {
            model.addAttribute("book", bookService.getById(bookParams.getBookID()).orElseThrow(IllegalArgumentException::new));
        }
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("authors", authorService.getAll());
        return "book-edit";
    }
}
