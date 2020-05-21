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
import ru.gonch.spring.rest.dto.GenreDto;
import ru.gonch.spring.service.GenreService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/api/genre")
    public List<GenreDto> getGenres() {
        return genreService.getAll().stream().map(GenreDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/api/genre/{id}")
    public GenreDto getGenre(@PathVariable("id") Long id) {
        return genreService.getById(id).map(GenreDto::toDto).orElseThrow(IllegalArgumentException::new);
    }

    @PostMapping("/api/genre")
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDto addGenre(@Valid @RequestBody GenreDto dto) {
        return GenreDto.toDto(genreService.save(GenreDto.toModel(dto)));
    }

    @PutMapping("/api/genre/{id}")
    public GenreDto editGenre(@PathVariable("id") Long id,
                              @Valid @RequestBody GenreDto dto) {
        if (!Objects.equals(id, dto.getId()) || genreService.getById(id).isEmpty()) {
            throw new IllegalArgumentException();
        }
        return GenreDto.toDto(genreService.save(GenreDto.toModel(dto)));
    }

    @DeleteMapping("/api/genre/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGenre(@PathVariable("id") Long id) {
        genreService.deleteById(id);
    }
}
