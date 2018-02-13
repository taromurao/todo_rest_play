# Todos schema

# --- !Ups

CREATE TABLE todos (
    id uuid PRIMARY KEY NOT NULL,
    title varchar(255) NOT NULL,
    content text,
    user_id uuid NOT NULL REFERENCES users (id)
);

# --- !Downs

DROP TABLE todos;