package ru.gonch.spring.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.gonch.spring.model.Book;
import ru.gonch.spring.rest.dto.BookDto;
import ru.gonch.spring.service.BookService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/book")
    public List<BookDto> getBooks(@RequestParam(value = "genreId", required = false) Long genreId,
                                  @RequestParam(value = "authorId", required = false) Long authorId) {
        if (genreId != null) {
            Stream<Book> stream = bookService.getBooksByGenreId(genreId).stream();
            if (authorId != null) {
                stream = stream.filter(book -> book.getAuthorId() == authorId);
            }
            return stream.map(BookDto::toDto).collect(Collectors.toList());
        } else if (authorId != null) {
            return bookService.getBooksByAuthorId(authorId).stream().map(BookDto::toDto).collect(Collectors.toList());
        } else {
            return bookService.getAll().stream().map(BookDto::toDto).collect(Collectors.toList());
        }
    }

    @GetMapping("/api/book/{id}")
    public BookDto getBook(@PathVariable("id") Long id) {
        return bookService.getById(id).map(BookDto::toDto).orElseThrow(IllegalArgumentException::new);
    }

    @PostMapping("/api/book")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto addBook(@Valid @RequestBody BookDto dto) {
        return BookDto.toDto(bookService.save(BookDto.toModel(dto)));
    }

    @PutMapping("/api/book/{id}")
    public BookDto editBook(@PathVariable("id") Long id,
                            @Valid @RequestBody BookDto dto) {
        if (!Objects.equals(id, dto.getId()) || bookService.getById(id).isEmpty()) {
            throw new IllegalArgumentException();
        }
        return BookDto.toDto(bookService.save(BookDto.toModel(dto)));
    }

    @DeleteMapping("/api/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
    }
}
