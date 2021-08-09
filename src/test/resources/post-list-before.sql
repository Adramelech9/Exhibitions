delete from post;

insert into post(id, title, filename, view, user_id) values
(1, 'first', 'default.png', 0 ,1),
(2, 'second', 'default.png', 0, 1),
(3, 'three', 'default.png', 0, 1);

alter sequence hibernate_sequence restart with 10;