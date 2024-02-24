drop TABLE IF EXISTS session_cookie_schema.session_cookies;
DROP SCHEMA if exists session_cookie_schema;

CREATE SCHEMA IF NOT EXISTS session_cookie_schema;

CREATE TABLE IF NOT EXISTS session_cookie_schema.session_cookies (
                            id identity primary key,
                            session_cookie varchar(32),
                            login varchar(32),
                            creation_time DATETIME
);