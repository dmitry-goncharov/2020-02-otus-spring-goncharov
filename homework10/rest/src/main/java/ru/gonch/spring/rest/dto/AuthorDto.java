package ru.gonch.spring.rest.dto;

import ru.gonch.spring.model.Author;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AuthorDto {
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

    public static AuthorDto toDto(Author model) {
        AuthorDto dto = new AuthorDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        return dto;
    }

    public static Author toModel(AuthorDto dto) {
        Author model = new Author();
        model.setId(dto.getId());
        model.setName(dto.getName());
        return model;
    }
}
