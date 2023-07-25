CREATE TABLE IF NOT EXISTS providers
(
    provider_id              character varying(255) NOT NULL,
    working_plan_provider_id character varying(255),
    primary key (provider_id),
    CONSTRAINT fk_working_plan_providers FOREIGN KEY (working_plan_provider_id) REFERENCES working_plans (provider_id),
    CONSTRAINT fk_user_providers FOREIGN KEY (provider_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS customers
(
    customer_id character varying(255) NOT NULL,
    primary key (customer_id)
);

CREATE TABLE IF NOT EXISTS works_providers
(
    work_id character varying(255) NOT NULL,
    user_id character varying(255) NOT NULL,
    PRIMARY KEY (work_id, user_id),
    CONSTRAINT fk_provider_works_providers FOREIGN KEY (user_id) REFERENCES providers (provider_id) ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_work_works_providers FOREIGN KEY (work_id) REFERENCES works (id) ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS corporate_customers
(
    customer_id  character varying(255) NOT NULL,
    vat_number   VARCHAR(256),
    company_name VARCHAR(256),
    PRIMARY KEY (customer_id),
    CONSTRAINT FK_corporate_customer_user FOREIGN KEY (customer_id)
        REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS retail_customers
(
    customer_id character varying(255) NOT NULL,
    vat_number  VARCHAR(256),
    PRIMARY KEY (customer_id),
    CONSTRAINT FK_retail_customer_user FOREIGN KEY (customer_id)
        REFERENCES users (id)
);