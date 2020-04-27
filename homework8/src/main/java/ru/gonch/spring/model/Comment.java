package ru.gonch.spring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "comments")
public class Comment {
    @Id
    private String id;

    @Field(name = "name")
    private String name;

    @Field(name = "comment")
    private String comment;

    @Field(name = "book_id")
    private String bookId;

    public Comment() {
        // Default constructor for spring data
    }

    public Comment(String name, String comment, String bookId) {
        this(null, name, comment, bookId);
    }

    public Comment(String id, String name, String comment, String bookId) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.bookId = bookId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public String getBookId() {
        return bookId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", bookId=" + bookId +
                '}';
    }
}
