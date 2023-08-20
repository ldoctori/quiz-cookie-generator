DROP TABLE cookie_schema.cookie_table;
DROP SCHEMA cookie_schema;

CREATE SCHEMA IF NOT EXISTS cookie_schema;

CREATE TABLE IF NOT EXISTS cookie_schema.cookie_table (
                            id identity primary key,
                            cookie varchar(32),
                            player1 varchar(32),
                            player2 varchar(32)
);

