package ru.gonch.spring.model;

public class Genre {
    private final long id;
    private final String name;

    public Genre(long id) {
        this.id = id;
        this.name = null;
    }

    public Genre(String name) {
        this.id = 0L;
        this.name = name;
    }

    public Genre(long id, String name) {
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
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
