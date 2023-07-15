CREATE TABLE IF NOT EXISTS service_type
(
    id     character varying(255) NOT NULL,
    effort smallint,
    name   character varying(255),
    primary key (id)
);
