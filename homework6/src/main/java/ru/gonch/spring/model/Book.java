package ru.gonch.spring.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "books")
@NamedEntityGraph(name = "genre-author-books-graph", attributeNodes = {
        @NamedAttributeNode("genre"),
        @NamedAttributeNode("author")
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(targetEntity = Genre.class, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToOne(targetEntity = Author.class, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "author_id")
    private Author author;

    public Book() {
        // Default constructor for jpa
    }

    public Book(String name, Genre genre, Author author) {
        this(0L, name, genre, author);
    }

    public Book(long id, String name, Genre genre, Author author) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Genre getGenre() {
        return genre;
    }

    public Author getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre=" + genre +
                ", author=" + author +
                '}';
    }
}
