package ru.gonch.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "comment", nullable = false, length = 1000)
    private String comment;

    @Column(name = "book_id", nullable = false)
    private long bookId;

    public Comment() {
        // Default constructor for jpa
    }

    public Comment(String name, String comment, long bookId) {
        this(0L, name, comment, bookId);
    }

    public Comment(long id, String name, String comment, long bookId) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.bookId = bookId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public long getBookId() {
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
