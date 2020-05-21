package ru.gonch.spring.rest.dto;

import ru.gonch.spring.model.Genre;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class GenreDto {
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String name;

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

    public static GenreDto toDto(Genre model) {
        GenreDto dto = new GenreDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        return dto;
    }

    public static Genre toModel(GenreDto dto) {
        Genre model = new Genre();
        model.setId(dto.getId());
        model.setName(dto.getName());
        return model;
    }
}
