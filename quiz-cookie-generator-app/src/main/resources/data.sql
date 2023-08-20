DROP TABLE if exists cookie_schema.mathematics;
DROP TABLE if exists cookie_schema.history;
DROP TABLE if exists cookie_schema.geography;
DROP TABLE if exists cookie_schema.chemistry;
DROP SCHEMA if exists cookie_schema;

CREATE SCHEMA IF NOT EXISTS cookie_schema;

CREATE TABLE IF NOT EXISTS cookie_schema.mathematics (
                            id identity primary key,
                            cookie varchar(32),
                            ttl BIGINT,
                            player1 varchar(32),
                            player2 varchar(32)
);

CREATE TABLE IF NOT EXISTS cookie_schema.history (
                            id identity primary key,
                            cookie varchar(32),
                            ttl BIGINT,
                            player1 varchar(32),
                            player2 varchar(32)
    );

CREATE TABLE IF NOT EXISTS cookie_schema.geography (
                            id identity primary key,
                            cookie varchar(32),
                            ttl BIGINT,
                            player1 varchar(32),
                            player2 varchar(32)
    );

CREATE TABLE IF NOT EXISTS cookie_schema.chemistry (
                            id bigint auto_increment,
                            cookie varchar(32),
                            ttl BIGINT,
                            player1 varchar(32),
                            player2 varchar(32)
    );

insert into cookie_schema.chemistry (cookie, ttl, player1, player2)
    values ('ss', 123225, 'Petuh', 'SUKA')