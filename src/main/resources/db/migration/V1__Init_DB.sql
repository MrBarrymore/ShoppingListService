create sequence hibernate_sequence start 1 increment 1;

create table purchase(
    id int8 not null,
    name varchar(255) not null,
    category varchar(255) not null,
    description varchar(2048),
    cost varchar(255),
    purchase_date date,
    is_bought bool,
    user_id int8,
    primary key (id)
);

create table user_role (
    user_id int8 not null,
    roles varchar(255)
);

create table usr (
        id int8 not null,
        activation_code varchar(255),
        active boolean not null,
        email varchar(255),
        password varchar(255) not null,
        username varchar(255) not null,
        primary key (id)
);

alter table if exists purchase
    add constraint purchase_user_fk
    foreign key (user_id) references usr;

alter table if exists user_role
    add constraint user_role_user_fk
    foreign key (user_id) references usr;