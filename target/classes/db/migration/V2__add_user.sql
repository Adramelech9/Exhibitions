insert into usr (id, username, password, active, email)
    values (1, 'admin', '1234', true, 'admin@gmail.com');

insert into user_role (user_id, roles)
    values (1, 'ADMIN');

create extension if not exists pgcrypto;
update usr set password = crypt(password, gen_salt('bf', 8));