package ru.gonch.spring.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.gonch.spring.model.Author;
import ru.gonch.spring.model.Book;
import ru.gonch.spring.model.Comment;
import ru.gonch.spring.model.Genre;

@ChangeLog
public class InitMongoDataChangeLog {
    private Genre novel;
    private Genre story;
    private Genre piece;

    private Author pushkin;
    private Author chekhov;
    private Author sholokhov;

    private Book eugeneOnegin;
    private Book cherryOrchard;

    @ChangeSet(order = "000", id = "dropDb", author = "goncharov", runAlways = true)
    public void dropDb(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initGenres", author = "goncharov", runAlways = true)
    public void initGenres(MongoTemplate template) {
        novel = template.save(newGenre("Novel"));
        story = template.save(newGenre("Story"));
        template.save(newGenre("Prose"));
        template.save(newGenre("Novelette"));
        piece = template.save(newGenre("Piece"));
    }

    @ChangeSet(order = "002", id = "initAuthors", author = "goncharov", runAlways = true)
    public void initAuthors(MongoTemplate template) {
        pushkin = template.save(newAuthor("Pushkin"));
        template.save(newAuthor("Turgenev"));
        template.save(newAuthor("Dostoevsky"));
        chekhov = template.save(newAuthor("Chekhov"));
        sholokhov = template.save(newAuthor("Sholokhov"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "goncharov", runAlways = true)
    public void initBooks(MongoTemplate template) {
        eugeneOnegin = template.save(newBook("Eugene Onegin", novel.getId(), pushkin.getId()));
        cherryOrchard = template.save(newBook("The Cherry Orchard", piece.getId(), chekhov.getId()));
        template.save(newBook("Don stories", story.getId(), sholokhov.getId()));
    }

    @ChangeSet(order = "004", id = "initComments", author = "goncharov", runAlways = true)
    public void initComments(MongoTemplate template) {
        template.save(newComment("From1", "Comment1", eugeneOnegin.getId()));
        template.save(newComment("From1", "Comment2", eugeneOnegin.getId()));
        template.save(newComment("From2", "Comment1", cherryOrchard.getId()));
    }

    private Genre newGenre(String name) {
        Genre genre = new Genre();
        genre.setName(name);
        return genre;
    }

    private Author newAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        return author;
    }

    private Book newBook(String name, String genreId, String authorId) {
        Book book = new Book();
        book.setName(name);
        book.setGenreId(genreId);
        book.setAuthorId(authorId);
        return book;
    }

    private Comment newComment(String name, String text, String bookId) {
        Comment comment = new Comment();
        comment.setName(name);
        comment.setText(text);
        comment.setBookId(bookId);
        return comment;
    }
}
