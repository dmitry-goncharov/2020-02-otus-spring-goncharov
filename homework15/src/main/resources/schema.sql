DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS users;

CREATE TABLE books (
                book_id IDENTITY NOT NULL,
                name VARCHAR(255) NOT NULL,
                CONSTRAINT book_id PRIMARY KEY (book_id)
);


CREATE TABLE users (
                user_id IDENTITY NOT NULL,
                name VARCHAR(255) NOT NULL,
                CONSTRAINT user_id PRIMARY KEY (user_id)
);