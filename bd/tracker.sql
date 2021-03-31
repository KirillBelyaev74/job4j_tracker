create database tracker;

drop database tracker;

create table items (
    id serial primary key,
    name varchar(50) not null);

drop table items;
