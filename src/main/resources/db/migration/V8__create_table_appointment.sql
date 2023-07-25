CREATE TABLE IF NOT EXISTS appointments
(
    id             character varying(255) NOT NULL,
    end_date       timestamp(6) without time zone,
    invoice_id     character varying(255),
    provider_id    character varying(255),
    start_date     timestamp(6) without time zone,
    status         character varying(255),
    status_changes json,
    work_id        character varying(255),
    customer_id    character varying(255),
    canceler_id    character varying(255),
    canceled_at timestamp(6),
    created_at timestamp(6),
    created_by character varying(255),
    last_modified_at timestamp(6),
    last_modified_by character varying(255),
    primary key (id),
    CONSTRAINT fk_provider_appointments FOREIGN KEY (provider_id) REFERENCES users (id),
    CONSTRAINT fk_customer_appointments FOREIGN KEY (customer_id) REFERENCES users (id),
    CONSTRAINT fk_invoices_appointments FOREIGN KEY (invoice_id) REFERENCES invoices (id),
    CONSTRAINT fk_work_appointments FOREIGN KEY (work_id) REFERENCES works (id)
);

