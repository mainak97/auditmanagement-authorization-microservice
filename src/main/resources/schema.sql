drop table user if exists;


create table user(
	id bigint not null,
	username varchar(255) not null,
	password varchar(255) not null,
	primary key(id)
);