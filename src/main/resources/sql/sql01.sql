create database springSecurity;

use springSecurity;

create table users(
                      id bigint primary key auto_increment,
                      username varchar(20) unique not null,
                      password varchar(100)
);



insert into users values(1,'zhangsan','123456');
insert into users values(2,'lisi','aabbcc');
