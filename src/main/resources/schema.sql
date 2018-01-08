create table user
(
   id long primary key,
   firstName varchar(255) not null,
   lastName varchar(255) not null,
   email varchar(255) not null,
   password varchar(255) not null
);

create table role
(
   id long primary key,
   name varchar(255) not null UNIQUE
);

create table user_role
(
   user_id long not null,
   role_id long not null,
);

create table user_follower
(
   user_id long not null,
   follower_id long not null,
);

create table message
(
   id long auto_increment primary key,
   user_id long not null,
   message varchar(280) 
);