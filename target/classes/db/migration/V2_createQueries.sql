create table hibernate_sequence (next_val bigint) engine=InnoDB

insert into hibernate_sequence values ( 1 )

create table post (
    id bigint not null,
    anons varchar(255),
    filename varchar(255),
    full_text varchar(2048) not null,
    title varchar(255),
    view integer not null,
    user_id bigint,
    primary key (id)) engine=InnoDB

create table user_role (
    user_id bigint not null,
    roles varchar(255)) engine=InnoDB

create table usr (
    id bigint not null,
    active bit not null,
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)) engine=InnoDB

alter table post
    add constraint post_user_fk
    foreign key (user_id) references usr (id)

alter table user_role
    add constraint user_role_user_fk
    foreign key (user_id) references usr (id)
