CREATE TABLE IF NOT EXISTS working_plans
(
    provider_id character varying(255) NOT NULL,
    friday      json,
    monday      json,
    saturday    json,
    sunday      json,
    thursday    json,
    tuesday     json,
    wednesday   json,
    primary key (provider_id)
);
