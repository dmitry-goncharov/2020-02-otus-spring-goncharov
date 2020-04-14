package ru.gonch.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "genre_id")
    private long genreId;

    @Column(name = "author_id")
    private long authorId;

    public Book() {
        // Default constructor for jpa
    }

    public Book(String name, long genreId, long authorId) {
        this(0L, name, genreId, authorId);
    }

    public Book(long id, String name, long genreId, long authorId) {
        this.id = id;
        this.name = name;
        this.genreId = genreId;
        this.authorId = authorId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getGenreId() {
        return genreId;
    }

    public long getAuthorId() {
        return authorId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genreId=" + genreId +
                ", authorId=" + authorId +
                '}';
    }
}
