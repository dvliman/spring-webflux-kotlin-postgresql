CREATE DATABASE demo;

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    name    TEXT NOT NULL,
    email   TEXT NOT NULL UNIQUE
);