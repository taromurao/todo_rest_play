# Users schema

# --- !Ups

CREATE TABLE users (
    id uuid NOT NULL,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    salt varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE users;