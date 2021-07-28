create table if not exists hibernate_sequence (next_val bigint) engine=InnoDB

insert into hibernate_sequence values ( 1 )

create table if not exists post (
    id bigint not null,
    anons varchar(255),
    filename varchar(255),
    full_text varchar(2048) not null,
    title varchar(255),
    view integer not null,
    user_id bigint,
    primary key (id)) engine=InnoDB

create table if not exists user_role (
    user_id bigint not null,
    roles varchar(255)) engine=InnoDB

create table if not exists usr (
    id bigint not null,
    active bit not null,
    email varchar(255),
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)) engine=InnoDB

alter table if exists post
    add constraint post_user_fk
    foreign key (user_id) references usr (id)

alter table if exists user_role
    add constraint user_role_user_fk
    foreign key (user_id) references usr (id)
