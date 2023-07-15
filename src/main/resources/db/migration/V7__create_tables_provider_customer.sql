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
    CONSTRAINT fk_provider_works_providers FOREIGN KEY (user_id) REFERENCES providers (provider_id),
    CONSTRAINT fk_work_works_providers FOREIGN KEY (work_id) REFERENCES works (id)
);

