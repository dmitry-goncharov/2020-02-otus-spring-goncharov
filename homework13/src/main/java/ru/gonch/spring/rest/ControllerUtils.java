package ru.gonch.spring.rest;

import ru.gonch.spring.model.Book;
import ru.gonch.spring.model.Comment;
import ru.gonch.spring.service.BookService;
import ru.gonch.spring.service.CommentService;

import java.util.List;

class ControllerUtils {
    private ControllerUtils() {
    }

    static List<Book> getBooks(BookService bookService, BookParams bookParams) {
        if (bookParams.getGenreID() != null) {
            return bookService.getBooksByGenreId(bookParams.getGenreID());
        } else if (bookParams.getAuthorID() != null) {
            return bookService.getBooksByAuthorId(bookParams.getAuthorID());
        } else {
            return bookService.getAll();
        }
    }

    static List<Comment> getComments(CommentService commentService, BookParams bookParams) {
        if (bookParams.getBookID() != null) {
            return commentService.getCommentsByBookId(bookParams.getBookID());
        } else {
            return commentService.getAll();
        }
    }
}
