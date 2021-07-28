insert into usr (id, username, password, active, email)
    values (1, 'admin', '$2a$08$wHNpwgLju5YeY1o1EQnIUet.t.EvkTbXel7AIU9jcUUyDFSHhqciO', true, 'admin@gmail.com');

insert into user_role (user_id, roles)
    values (1, 'ADMIN');
