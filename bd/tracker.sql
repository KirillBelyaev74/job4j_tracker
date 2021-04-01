create database tracker;

drop database tracker;

create table items (
    id serial primary key,
    name varchar(50) not null,
    description text,
    created timestamp);

drop table items;
