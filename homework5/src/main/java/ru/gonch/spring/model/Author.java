package ru.gonch.spring.model;

public class Author {
    private final long id;
    private final String name;

    public Author(long id) {
        this.id = id;
        this.name = null;
    }

    public Author(String name) {
        this.id = 0L;
        this.name = name;
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

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
