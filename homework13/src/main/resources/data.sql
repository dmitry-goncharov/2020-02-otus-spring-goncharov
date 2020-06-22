insert into genres (`name`) values ('Novel');
insert into genres (`name`) values ('Story');
insert into genres (`name`) values ('Prose');
insert into genres (`name`) values ('Novelette');
insert into genres (`name`) values ('Piece');

insert into authors (`name`) values ('Pushkin');
insert into authors (`name`) values ('Turgenev');
insert into authors (`name`) values ('Dostoevsky');
insert into authors (`name`) values ('Chekhov');
insert into authors (`name`) values ('Sholokhov');

insert into books (`name`,`genre_id`,`author_id`) values ('Eugene Onegin',1,1);
insert into books (`name`,`genre_id`,`author_id`) values ('The Cherry Orchard',5,4);
insert into books (`name`,`genre_id`,`author_id`) values ('Don stories',2,5);

insert into comments (`name`,`comment`,`book_id`) values ('From1','Comment1',1);
insert into comments (`name`,`comment`,`book_id`) values ('From1','Comment2',1);
insert into comments (`name`,`comment`,`book_id`) values ('From1','Comment1',2);

insert into users (`login`,`password`,`authority`) values ('admin','$2a$10$5wDgfjZczbayzF89ZHX38eJc.tV6.e6lgdJ4nCd14ovZ4IObziloK','ROLE_ADMIN');
insert into users (`login`,`password`,`authority`) values ('editor','$2a$10$wUelMVb8kJg9eqHdHb8ih.HcCoP11wYB1Xwb4PRyA/mwgNtqLq6Je','ROLE_EDITOR');
insert into users (`login`,`password`,`authority`) values ('user','$2a$10$.p8N0icTh5efCafQ8PHilOaMu.BpZ2F7bEZtLPI2X6xdqXG9LSdxO','ROLE_USER');