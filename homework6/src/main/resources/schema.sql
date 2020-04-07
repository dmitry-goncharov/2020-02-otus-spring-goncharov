DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS comments;



CREATE TABLE genres (
                genre_id IDENTITY NOT NULL,
                name VARCHAR(255) NOT NULL,
                CONSTRAINT genre_id PRIMARY KEY (genre_id)
);


CREATE TABLE authors (
                author_id IDENTITY NOT NULL,
                name VARCHAR(255) NOT NULL,
                CONSTRAINT author_id PRIMARY KEY (author_id)
);


CREATE TABLE books (
                book_id IDENTITY NOT NULL,
                name VARCHAR(255) NOT NULL,
                genre_id BIGINT NOT NULL,
                author_id BIGINT NOT NULL,
                CONSTRAINT book_id PRIMARY KEY (book_id)
);


CREATE TABLE comments (
                comment_id IDENTITY NOT NULL,
                name VARCHAR(255) NOT NULL,
                comment VARCHAR(1000) NOT NULL,
                book_id BIGINT NOT NULL,
                CONSTRAINT comment_id PRIMARY KEY (comment_id)
);



ALTER TABLE books ADD CONSTRAINT genres_books_fk
FOREIGN KEY (genre_id)
REFERENCES genres (genre_id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE books ADD CONSTRAINT authors_books_fk
FOREIGN KEY (author_id)
REFERENCES authors (author_id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE comments ADD CONSTRAINT books_comments_fk
FOREIGN KEY (book_id)
REFERENCES books (book_id)
ON DELETE CASCADE
ON UPDATE NO ACTION;