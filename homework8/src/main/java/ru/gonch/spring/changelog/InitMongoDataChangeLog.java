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
        novel = template.save(new Genre("Novel"));
        story = template.save(new Genre("Story"));
        Genre prose = template.save(new Genre("Prose"));
        Genre novelette = template.save(new Genre("Novelette"));
        piece = template.save(new Genre("Piece"));
    }

    @ChangeSet(order = "002", id = "initAuthors", author = "goncharov", runAlways = true)
    public void initAuthors(MongoTemplate template) {
        pushkin = template.save(new Author("Pushkin"));
        Author turgenev = template.save(new Author("Turgenev"));
        Author dostoevsky = template.save(new Author("Dostoevsky"));
        chekhov = template.save(new Author("Chekhov"));
        sholokhov = template.save(new Author("Sholokhov"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "goncharov", runAlways = true)
    public void initBooks(MongoTemplate template) {
        eugeneOnegin = template.save(new Book("Eugene Onegin", novel.getId(), pushkin.getId()));
        cherryOrchard = template.save(new Book("The Cherry Orchard", piece.getId(), chekhov.getId()));
        Book donStories = template.save(new Book("Don stories", story.getId(), sholokhov.getId()));
    }

    @ChangeSet(order = "004", id = "initComments", author = "goncharov", runAlways = true)
    public void initComments(MongoTemplate template) {
        template.save(new Comment("From1", "Comment1", eugeneOnegin.getId()));
        template.save(new Comment("From1", "Comment2", eugeneOnegin.getId()));
        template.save(new Comment("From1", "Comment1", cherryOrchard.getId()));
    }
}
