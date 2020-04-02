package ru.gonch.spring.model;

public class Book {
    private final long id;
    private final String name;
    private final Genre genre;
    private final Author author;

    public Book(String name, Genre genre, Author author) {
        this.id = 0L;
        this.name = name;
        this.genre = genre;
        this.author = author;
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
