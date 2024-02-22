-- DROP TABLE if exists session_cookie_schema.mathematics;
-- DROP TABLE if exists session_cookie_schema.history;
-- DROP TABLE if exists session_cookie_schema.geography;
-- DROP TABLE if exists session_cookie_schema.chemistry;
-- DROP SCHEMA if exists session_cookie_schema;

CREATE SCHEMA IF NOT EXISTS session_cookie_schema;

CREATE TABLE IF NOT EXISTS session_cookie_schema.session_cookies (
                            id identity primary key,
                            session_cookie varchar(32),
                            login varchar(32),
                            creationTime DATETIME
);