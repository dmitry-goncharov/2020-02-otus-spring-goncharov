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
import ru.gonch.spring.rest.dto.CommentDto;
import ru.gonch.spring.service.CommentService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/api/comment")
    public List<CommentDto> getComments(@RequestParam(value = "bookId", required = false) Long bookId) {
        if (bookId != null) {
            return commentService.getCommentsByBookId(bookId).stream().map(CommentDto::toDto).collect(Collectors.toList());
        } else {
            return commentService.getAll().stream().map(CommentDto::toDto).collect(Collectors.toList());
        }
    }

    @GetMapping("/api/comment/{id}")
    public CommentDto getComment(@PathVariable("id") Long id) {
        return commentService.getById(id).map(CommentDto::toDto).orElseThrow(IllegalArgumentException::new);
    }

    @PostMapping("/api/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(@Valid @RequestBody CommentDto dto) {
        return CommentDto.toDto(commentService.save(CommentDto.toModel(dto)));
    }

    @PutMapping("/api/comment/{id}")
    public CommentDto editComment(@PathVariable("id") Long id,
                                  @Valid @RequestBody CommentDto dto) {
        if (!Objects.equals(id, dto.getId()) || commentService.getById(id).isEmpty()) {
            throw new IllegalArgumentException();
        }
        return CommentDto.toDto(commentService.save(CommentDto.toModel(dto)));
    }

    @DeleteMapping("/api/comment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable("id") Long id) {
        commentService.deleteById(id);
    }
}
