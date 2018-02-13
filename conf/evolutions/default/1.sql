# Users schema

# --- !Ups

CREATE TABLE users (
    id uuid PRIMARY KEY NOT NULL,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    salt varchar(255) NOT NULL
);

# --- !Downs

DROP TABLE users;