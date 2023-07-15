CREATE TABLE IF NOT EXISTS users
(
    id           character varying(255) NOT NULL,
    city         character varying(255),
    email        character varying(255),
    first_name   character varying(255),
    last_name    character varying(255),
    phone_number character varying(255),
    post_code    character varying(255),
    provider     boolean                NOT NULL,
    street       character varying(255),
    password     character varying(255),
    primary key (id)
);

