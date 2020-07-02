package ru.gonch.spring.shell;

import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.gonch.spring.repository.mongo.AuthorMongoRepository;
import ru.gonch.spring.repository.mongo.BookMongoRepository;
import ru.gonch.spring.repository.mongo.CommentMongoRepository;
import ru.gonch.spring.repository.mongo.GenreMongoRepository;

import static ru.gonch.spring.config.MigrationJobConfig.MIGRATION_JOB;

@ShellComponent
public class GenreShell {
    private final JobOperator jobOperator;
    private final GenreMongoRepository genreRepository;
    private final AuthorMongoRepository authorRepository;
    private final BookMongoRepository bookRepository;
    private final CommentMongoRepository commentRepository;

    public GenreShell(JobOperator jobOperator,
                      GenreMongoRepository genreRepository,
                      AuthorMongoRepository authorRepository,
                      BookMongoRepository bookRepository,
                      CommentMongoRepository commentRepository) {
        this.jobOperator = jobOperator;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    @ShellMethod(value = "Start migration job", key = {"start"})
    public String startMigration() {
        try {
            return String.format("Migration job has been started with id %d", jobOperator.start(MIGRATION_JOB, ""));
        } catch (Exception e) {
            return "Error starting migration job";
        }
    }

    @ShellMethod(value = "Get status migration job", key = {"status"})
    public String getStatusMigrationJob(@ShellOption long id) {
        try {
            return String.format("Status migration job: %s", jobOperator.getSummary(id));
        } catch (Exception e) {
            return "Error getting status migration job";
        }
    }

    @ShellMethod(value = "Get all genres", key = {"genres"})
    public String getGenres() {
        return String.format("Genres: %s", genreRepository.findAll());
    }

    @ShellMethod(value = "Get all authors", key = {"authors"})
    public String getAuthors() {
        return String.format("Authors: %s", authorRepository.findAll());
    }

    @ShellMethod(value = "Get all books", key = {"books"})
    public String getBooks() {
        return String.format("Books: %s", bookRepository.findAll());
    }

    @ShellMethod(value = "Get all comments", key = {"comments"})
    public String getComments() {
        return String.format("Comments: %s", commentRepository.findAll());
    }
}
