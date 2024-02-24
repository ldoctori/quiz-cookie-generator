DROP TABLE if exists game_cookie_schema.mathematics;
DROP TABLE if exists game_cookie_schema.history;
DROP TABLE if exists game_cookie_schema.geography;
DROP TABLE if exists game_cookie_schema.chemistry;
DROP SCHEMA if exists game_cookie_schema;

CREATE SCHEMA IF NOT EXISTS game_cookie_schema;

CREATE TABLE IF NOT EXISTS game_cookie_schema.mathematics (
                            id bigint auto_increment,
                            game_cookie varchar(32),
                            creation_time DATETIME,
                            session_of_player1 varchar(32),
                            session_of_player2 varchar(32)
);

CREATE TABLE IF NOT EXISTS game_cookie_schema.history (
                            id bigint auto_increment,
                            game_cookie varchar(32),
                            creation_time DATETIME,
                            session_of_player1 varchar(32),
                            session_of_player2 varchar(32)
    );

CREATE TABLE IF NOT EXISTS game_cookie_schema.geography (
                            id bigint auto_increment,
                            game_cookie varchar(32),
                            creation_time DATETIME,
                            session_of_player1 varchar(32),
                            session_of_player2 varchar(32)
    );

CREATE TABLE IF NOT EXISTS game_cookie_schema.chemistry (
                            id bigint auto_increment,
                            game_cookie varchar(32),
                            creation_time DATETIME,
                            session_of_player1 varchar(32),
                            session_of_player2 varchar(32)
    );