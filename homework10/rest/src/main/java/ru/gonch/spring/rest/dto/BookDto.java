package ru.gonch.spring.rest.dto;

import ru.gonch.spring.model.Book;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookDto {
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    private Long genreId;

    @NotNull
    private Long authorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public static BookDto toDto(Book model) {
        BookDto dto = new BookDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setGenreId(model.getGenreId());
        dto.setAuthorId(model.getAuthorId());
        return dto;
    }

    public static Book toModel(BookDto dto) {
        Book model = new Book();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setGenreId(dto.getGenreId());
        model.setAuthorId(dto.getAuthorId());
        return model;
    }
}
