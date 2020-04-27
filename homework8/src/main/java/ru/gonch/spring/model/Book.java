package ru.gonch.spring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "books")
public class Book {
    @Id
    private String id;

    @Field(name = "name")
    private String name;

    @Field(name = "genre_id")
    private String genreId;

    @Field(name = "author_id")
    private String authorId;

    public Book() {
        // Default constructor for spring data
    }

    public Book(String name, String genreId, String authorId) {
        this(null, name, genreId, authorId);
    }

    public Book(String id, String name, String genreId, String authorId) {
        this.id = id;
        this.name = name;
        this.genreId = genreId;
        this.authorId = authorId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenreId() {
        return genreId;
    }

    public String getAuthorId() {
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
