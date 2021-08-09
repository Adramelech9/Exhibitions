delete from user_role;
delete from usr;

insert into usr (id, active, password, username) values
(1, true, '$2a$08$w7d70CTpVgxAlWpD8kwT9ecyN2Be3eaznruOFE3GIVbqkYud3Bf0m', 'admin'),
(2, true, '$2a$08$w7d70CTpVgxAlWpD8kwT9ecyN2Be3eaznruOFE3GIVbqkYud3Bf0m', 'user1');

insert into user_role (user_id, roles) values
(1, 'ADMIN'),
(2, 'USER');

