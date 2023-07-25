INSERT INTO roles (id, name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_PROVIDER'),
       (3, 'ROLE_CUSTOMER'),
       (4, 'ROLE_CUSTOMER_CORPORATE'),
       (5, 'ROLE_CUSTOMER_RETAIL');

-- INSERT admin account with first_name: 'admin' and password 'qwerty123'
INSERT INTO users (id, first_name, last_name, password, provider)
VALUES ('f10fba6d-2f5c-42fa-a70e-62808c125da7', 'admin', 'system',
        '$2a$10$EqKcp1WFKVQISheBxkQJoOqFbsWDzGJXRz/tjkGq85IZKJJ1IipYi', true);
INSERT INTO user_roles (user_id, role_id)
VALUES ('f10fba6d-2f5c-42fa-a70e-62808c125da7', 1);

-- INSERT provider account with first_name: 'provider' and password 'qwerty123'
INSERT INTO users (id, first_name, last_name, password, provider)
VALUES ('90507c2b-dc62-4998-8b63-edd881055dcc', 'provider', 'silva',
        '$2a$10$EqKcp1WFKVQISheBxkQJoOqFbsWDzGJXRz/tjkGq85IZKJJ1IipYi', true);
INSERT INTO providers (provider_id)
VALUES ('90507c2b-dc62-4998-8b63-edd881055dcc');
INSERT INTO user_roles (user_id, role_id)
VALUES ('90507c2b-dc62-4998-8b63-edd881055dcc', 2);


-- INSERT retail customer account with first_name: 'customer_r' and password 'qwerty123'
INSERT INTO users (id, first_name, last_name, password, provider)
VALUES ('7dbe4452-f2ea-4574-bcd5-5e9b67908fa0', 'customer_r', 'silva',
        '$2a$10$EqKcp1WFKVQISheBxkQJoOqFbsWDzGJXRz/tjkGq85IZKJJ1IipYi', false);
INSERT INTO customers (customer_id)
VALUES ('7dbe4452-f2ea-4574-bcd5-5e9b67908fa0');
INSERT INTO retail_customers (customer_id)
VALUES ('7dbe4452-f2ea-4574-bcd5-5e9b67908fa0');
INSERT INTO user_roles (user_id, role_id)
VALUES ('7dbe4452-f2ea-4574-bcd5-5e9b67908fa0', 3);
INSERT INTO user_roles (user_id, role_id)
VALUES ('7dbe4452-f2ea-4574-bcd5-5e9b67908fa0', 5);

-- INSERT corporate customer account with first_name: 'customer_c' and password 'qwerty123'
INSERT INTO users (id, first_name, last_name, password, provider)
VALUES ('e8918208-67ce-439b-9dad-6986a94e3e1b', 'customer_c', 'silva',
        '$2a$10$EqKcp1WFKVQISheBxkQJoOqFbsWDzGJXRz/tjkGq85IZKJJ1IipYi', false);
INSERT INTO customers (customer_id)
VALUES ('e8918208-67ce-439b-9dad-6986a94e3e1b');
INSERT INTO corporate_customers (customer_id, vat_number, company_name)
VALUES ('e8918208-67ce-439b-9dad-6986a94e3e1b', '123456789', 'Company name');
INSERT INTO user_roles (user_id, role_id)
VALUES ('e8918208-67ce-439b-9dad-6986a94e3e1b', 3);
INSERT INTO user_roles (user_id, role_id)
VALUES ('e8918208-67ce-439b-9dad-6986a94e3e1b', 4);

INSERT INTO works (id, name, duration, price, target_customer, description)
VALUES ('96f48aaf-08fd-47ca-bfa6-650ea46ea081', 'English lesson', 60, 100.00, 'retail',
        'This is english lesson with duration 60 minutes and price 100 pln');

INSERT INTO works_providers
VALUES ('96f48aaf-08fd-47ca-bfa6-650ea46ea081', '90507c2b-dc62-4998-8b63-edd881055dcc');
INSERT INTO working_plans
VALUES ('90507c2b-dc62-4998-8b63-edd881055dcc',
        '{"workingHours":{"start":[6,0],"end":[18,0]},"breaks":[],"timePeroidsWithBreaksExcluded":[{"start":[6,0],"end":[18,0]}]}',
        '{"workingHours":{"start":[6,0],"end":[18,0]},"breaks":[],"timePeroidsWithBreaksExcluded":[{"start":[6,0],"end":[18,0]}]}',
        '{"workingHours":{"start":[6,0],"end":[18,0]},"breaks":[],"timePeroidsWithBreaksExcluded":[{"start":[6,0],"end":[18,0]}]}',
        '{"workingHours":{"start":[6,0],"end":[18,0]},"breaks":[],"timePeroidsWithBreaksExcluded":[{"start":[6,0],"end":[18,0]}]}',
        '{"workingHours":{"start":[6,0],"end":[18,0]},"breaks":[],"timePeroidsWithBreaksExcluded":[{"start":[6,0],"end":[18,0]}]}',
        '{"workingHours":{"start":[6,0],"end":[18,0]},"breaks":[],"timePeroidsWithBreaksExcluded":[{"start":[6,0],"end":[18,0]}]}',
        '{"workingHours":{"start":[6,0],"end":[18,0]},"breaks":[],"timePeroidsWithBreaksExcluded":[{"start":[6,0],"end":[18,0]}]}');