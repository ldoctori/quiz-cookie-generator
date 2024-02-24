drop TABLE IF EXISTS auth_schema.auth_data;
DROP SCHEMA if exists auth_schema;

CREATE SCHEMA IF NOT EXISTS auth_schema;

CREATE TABLE IF NOT EXISTS auth_schema.auth_data (
                            id identity primary key,
                            login varchar(32),
                            password varchar(32),
                            registration_date DATETIME
);