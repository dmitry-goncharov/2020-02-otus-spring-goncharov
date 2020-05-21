package ru.gonch.spring.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.gonch.spring.rest.dto.AuthorDto;
import ru.gonch.spring.service.AuthorService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/api/author")
    public List<AuthorDto> getAuthors() {
        return authorService.getAll().stream().map(AuthorDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/api/author/{id}")
    public AuthorDto getAuthor(@PathVariable("id") Long id) {
        return authorService.getById(id).map(AuthorDto::toDto).orElseThrow(IllegalArgumentException::new);
    }

    @PostMapping("/api/author")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto addAuthor(@Valid @RequestBody AuthorDto dto) {
        return AuthorDto.toDto(authorService.save(AuthorDto.toModel(dto)));
    }

    @PutMapping("/api/author/{id}")
    public AuthorDto editAuthor(@PathVariable("id") Long id,
                                @Valid @RequestBody AuthorDto dto) {
        if (!Objects.equals(id, dto.getId()) || authorService.getById(id).isEmpty()) {
            throw new IllegalArgumentException();
        }
        return AuthorDto.toDto(authorService.save(AuthorDto.toModel(dto)));
    }

    @DeleteMapping("/api/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAuthor(@PathVariable("id") Long id) {
        authorService.deleteById(id);
    }
}
