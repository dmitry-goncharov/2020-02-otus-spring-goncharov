package ru.gonch.spring.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "authors")
@NamedEntityGraph(
        name = "graph.author.books",
        attributeNodes = @NamedAttributeNode(value = "books", subgraph = "book.comments"),
        subgraphs = @NamedSubgraph(name = "book.comments", attributeNodes = @NamedAttributeNode("comments"))
)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "authorId")
    private Set<Book> books;

    public Author() {
        // Default constructor for jpa
    }

    public Author(long id) {
        this(id, null);
    }

    public Author(String name) {
        this(0L, name);
    }

    public Author(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", books=" + books +
                '}';
    }
}
