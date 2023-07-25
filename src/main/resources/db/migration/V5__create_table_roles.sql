CREATE TABLE IF NOT EXISTS roles
(
    id   bigint NOT NULL,
    name character varying(255),
    primary key (id)
);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id character varying(255) NOT NULL,
    role_id bigint                 NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_role_user_roles FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT fk_user_user_roles FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

