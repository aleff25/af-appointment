CREATE TABLE IF NOT EXISTS works
(
    id              character varying(255) NOT NULL,
    description     character varying(255),
    duration        integer                NOT NULL,
    name            character varying(255),
    price           double precision       NOT NULL,
    target_customer character varying(255),
    primary key (id)
);
