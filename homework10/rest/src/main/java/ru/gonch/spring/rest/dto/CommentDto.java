package ru.gonch.spring.rest.dto;

import ru.gonch.spring.model.Comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentDto {
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(max = 1000)
    private String text;

    @NotNull
    private Long bookId;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public static CommentDto toDto(Comment model) {
        CommentDto dto = new CommentDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setText(model.getText());
        dto.setBookId(model.getBookId());
        return dto;
    }

    public static Comment toModel(CommentDto dto) {
        Comment model = new Comment();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setText(dto.getText());
        model.setBookId(dto.getBookId());
        return model;
    }
}
