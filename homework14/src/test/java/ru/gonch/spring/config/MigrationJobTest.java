package ru.gonch.spring.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gonch.spring.repository.mongo.AuthorMongoRepository;
import ru.gonch.spring.repository.mongo.BookMongoRepository;
import ru.gonch.spring.repository.mongo.CommentMongoRepository;
import ru.gonch.spring.repository.mongo.GenreMongoRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.batch.core.ExitStatus.COMPLETED;
import static ru.gonch.spring.config.MigrationJobConfig.MIGRATION_JOB;

@SpringBootTest
@SpringBatchTest
class MigrationJobTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private GenreMongoRepository genreRepository;
    @Autowired
    private AuthorMongoRepository authorRepository;
    @Autowired
    private BookMongoRepository bookRepository;
    @Autowired
    private CommentMongoRepository commentRepository;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void jobTest() throws Exception {
        assertEquals(0, genreRepository.count());
        assertEquals(0, authorRepository.count());
        assertEquals(0, bookRepository.count());
        assertEquals(0, commentRepository.count());

        Job job = jobLauncherTestUtils.getJob();

        assertNotNull(job);
        assertEquals(MIGRATION_JOB, job.getName());

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        assertEquals(COMPLETED, jobExecution.getExitStatus());

        assertEquals(5, genreRepository.count());
        assertEquals(5, authorRepository.count());
        assertEquals(3, bookRepository.count());
        assertEquals(3, commentRepository.count());
    }
}
