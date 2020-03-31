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