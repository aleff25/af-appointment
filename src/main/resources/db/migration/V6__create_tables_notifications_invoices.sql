CREATE TABLE IF NOT EXISTS invoices
(
    id           character varying(255) NOT NULL,
    issued       timestamp(6) without time zone,
    number       character varying(255),
    status       character varying(255),
    total_amount double precision       NOT NULL,
    primary key (id)
);

CREATE TABLE IF NOT EXISTS notifications
(
    id         character varying(255) NOT NULL,
    created_at date,
    is_read    boolean                NOT NULL,
    message    character varying(255),
    title      character varying(255),
    url        character varying(255),
    user_id    character varying(255),
    primary key (id),
    CONSTRAINT fk_user_notifications FOREIGN KEY (user_id) REFERENCES users (id)
);
