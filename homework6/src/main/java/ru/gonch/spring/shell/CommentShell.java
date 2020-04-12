package ru.gonch.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.gonch.spring.model.Comment;
import ru.gonch.spring.service.CommentService;

import java.util.List;
import java.util.Optional;

@ShellComponent
public class CommentShell {
    private final CommentService commentService;

    public CommentShell(CommentService commentService) {
        this.commentService = commentService;
    }

    @ShellMethod(value = "Add comment", key = {"add-comment"})
    public String addComment(@ShellOption String name,
                             @ShellOption String comment,
                             @ShellOption long bookId) {
        try {
            long id = commentService.save(new Comment(name, comment, bookId));
            return String.format("Comment with id %d has been added", id);
        } catch (IllegalArgumentException e) {
            return "Bad request: " + e.getMessage();
        }
    }

    @ShellMethod(value = "Get comment by id", key = {"get-comment"})
    public String getComment(@ShellOption long id) {
        Optional<Comment> comment = commentService.getById(id);
        return String.format("Comment: %s", comment.isPresent() ? comment.get() : "none");
    }

    @ShellMethod(value = "Get all comments", key = {"get-comments"})
    public String getComments() {
        List<Comment> comments = commentService.getAll();
        return String.format("Comments: %s", comments);
    }

    @ShellMethod(value = "Update comment by id", key = {"upd-comment"})
    public String updateComment(@ShellOption long id,
                                @ShellOption String name,
                                @ShellOption String comment,
                                @ShellOption long bookId) {
        try {
            if (commentService.update(new Comment(id, name, comment, bookId))) {
                return String.format("Comment with id %d has been updated", id);
            } else {
                return getNotFoundString(id);
            }
        } catch (IllegalArgumentException e) {
            return "Bad request: " + e.getMessage();
        }
    }

    @ShellMethod(value = "Delete comment command", key = {"del-comment"})
    public String deleteComment(@ShellOption long id) {
        if (commentService.deleteById(id)) {
            return String.format("Comment with id %d has been deleted", id);
        } else {
            return getNotFoundString(id);
        }
    }

    private String getNotFoundString(long id) {
        return String.format("Comment with id %d not found", id);
    }
}
