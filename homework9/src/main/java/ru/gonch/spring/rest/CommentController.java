package ru.gonch.spring.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gonch.spring.model.Comment;
import ru.gonch.spring.service.BookService;
import ru.gonch.spring.service.CommentService;

import javax.validation.Valid;

@Controller
public class CommentController {
    private final CommentService commentService;
    private final BookService bookService;

    public CommentController(CommentService commentService, BookService bookService) {
        this.commentService = commentService;
        this.bookService = bookService;
    }

    @GetMapping("/comment")
    public String listPage(BookParams bookParams, Model model) {
        return prepareListPage(bookParams, model);
    }

    @GetMapping("/comment/add")
    public String addPage(BookParams bookParams, Comment comment, Model model) {
        return prepareAddPage(bookParams, model);
    }

    @GetMapping("/comment/edit")
    public String editPage(BookParams bookParams, @RequestParam("id") long id, Model model) {
        return prepareEditPage(bookParams, id, model, true);
    }

    @PostMapping("/comment/add")
    public String addObject(BookParams bookParams, @Valid Comment comment, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return prepareAddPage(bookParams, model);
        } else {
            commentService.save(comment);
            return prepareListPage(bookParams, model);
        }
    }

    @PostMapping("/comment/edit")
    public String editObject(BookParams bookParams, @Valid Comment comment, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return prepareEditPage(bookParams, comment.getId(), model, false);
        } else {
            commentService.save(comment);
            return prepareListPage(bookParams, model);
        }
    }

    @GetMapping("/comment/delete")
    public String deleteObject(BookParams bookParams, @RequestParam("id") long id, Model model) {
        commentService.deleteById(id);
        return prepareListPage(bookParams, model);
    }

    private String prepareListPage(BookParams bookParams, Model model) {
        model.addAttribute("comments", ControllerUtils.getComments(commentService, bookParams));
        if (bookParams.getBookID() != null) {
            model.addAttribute("book", bookService.getById(bookParams.getBookID()).orElseThrow(IllegalArgumentException::new));
        }
        return "comment-list";
    }

    private String prepareAddPage(BookParams bookParams, Model model) {
        model.addAttribute("books", ControllerUtils.getBooks(bookService, bookParams));
        return "comment-add";
    }

    private String prepareEditPage(BookParams bookParams, @RequestParam("id") long id, Model model, boolean addObjectById) {
        if (addObjectById) {
            model.addAttribute("comment", commentService.getById(id).orElseThrow(IllegalArgumentException::new));
        }
        model.addAttribute("books", ControllerUtils.getBooks(bookService, bookParams));
        return "comment-edit";
    }
}
