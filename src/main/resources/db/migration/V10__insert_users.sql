-- INSERT admin account with username: 'admin' and password 'qwerty123'
INSERT INTO users (id, username, password)
VALUES ('f10fba6d-2f5c-42fa-a70e-62808c125da7', 'admin', '$2a$10$EqKcp1WFKVQISheBxkQJoOqFbsWDzGJXRz/tjkGq85IZKJJ1IipYi');
INSERT INTO users_roles (user_id, role_id)
VALUES ('f10fba6d-2f5c-42fa-a70e-62808c125da7', 1);

-- INSERT provider account with username: 'provider' and password 'qwerty123'
INSERT INTO users (id, username, password)
VALUES ('90507c2b-dc62-4998-8b63-edd881055dcc', 'provider', '$2a$10$EqKcp1WFKVQISheBxkQJoOqFbsWDzGJXRz/tjkGq85IZKJJ1IipYi');
INSERT INTO providers (provider_id)
VALUES ('90507c2b-dc62-4998-8b63-edd881055dcc');
INSERT INTO users_roles (user_id, role_id)
VALUES ('90507c2b-dc62-4998-8b63-edd881055dcc', 2);


-- INSERT retail customer account with username: 'customer_r' and password 'qwerty123'
INSERT INTO users (id, username, password)
VALUES ('7dbe4452-f2ea-4574-bcd5-5e9b67908fa0', 'customer_r', '$2a$10$EqKcp1WFKVQISheBxkQJoOqFbsWDzGJXRz/tjkGq85IZKJJ1IipYi');
INSERT INTO customers (customer_id)
VALUES ('7dbe4452-f2ea-4574-bcd5-5e9b67908fa0');
INSERT INTO retail_customers (customer_id)
VALUES ('7dbe4452-f2ea-4574-bcd5-5e9b67908fa0');
INSERT INTO users_roles (user_id, role_id)
VALUES ('7dbe4452-f2ea-4574-bcd5-5e9b67908fa0', 3);
INSERT INTO users_roles (user_id, role_id)
VALUES ('7dbe4452-f2ea-4574-bcd5-5e9b67908fa0', 5);

-- INSERT corporate customer account with username: 'customer_c' and password 'qwerty123'
INSERT INTO users (id, username, password)
VALUES ('e8918208-67ce-439b-9dad-6986a94e3e1b', 'customer_c', '$2a$10$EqKcp1WFKVQISheBxkQJoOqFbsWDzGJXRz/tjkGq85IZKJJ1IipYi');
INSERT INTO customers (id_customer)
VALUES ('e8918208-67ce-439b-9dad-6986a94e3e1b');
INSERT INTO corporate_customers (id_customer, vat_number, company_name)
VALUES ('e8918208-67ce-439b-9dad-6986a94e3e1b', '123456789', 'Company name');
INSERT INTO users_roles (user_id, role_id)
VALUES ('e8918208-67ce-439b-9dad-6986a94e3e1b', 3);
INSERT INTO users_roles (user_id, role_id)
VALUES ('e8918208-67ce-439b-9dad-6986a94e3e1b', 4);

INSERT INTO works (id, name, duration, price, editable, target, description)
VALUES ('96f48aaf-08fd-47ca-bfa6-650ea46ea081', 'English lesson', 60, 100.00, true, 'retail',
        'This is english lesson with duration 60 minutes and price 100 pln');

INSERT INTO works_providers
VALUES (2, 1);
INSERT INTO working_plans
VALUES (2,
        '{"workingHours":{"start":[6,0],"end":[18,0]},"breaks":[],"timePeroidsWithBreaksExcluded":[{"start":[6,0],"end":[18,0]}]}',
        '{"workingHours":{"start":[6,0],"end":[18,0]},"breaks":[],"timePeroidsWithBreaksExcluded":[{"start":[6,0],"end":[18,0]}]}',
        '{"workingHours":{"start":[6,0],"end":[18,0]},"breaks":[],"timePeroidsWithBreaksExcluded":[{"start":[6,0],"end":[18,0]}]}',
        '{"workingHours":{"start":[6,0],"end":[18,0]},"breaks":[],"timePeroidsWithBreaksExcluded":[{"start":[6,0],"end":[18,0]}]}',
        '{"workingHours":{"start":[6,0],"end":[18,0]},"breaks":[],"timePeroidsWithBreaksExcluded":[{"start":[6,0],"end":[18,0]}]}',
        '{"workingHours":{"start":[6,0],"end":[18,0]},"breaks":[],"timePeroidsWithBreaksExcluded":[{"start":[6,0],"end":[18,0]}]}',
        '{"workingHours":{"start":[6,0],"end":[18,0]},"breaks":[],"timePeroidsWithBreaksExcluded":[{"start":[6,0],"end":[18,0]}]}');