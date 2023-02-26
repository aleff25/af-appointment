CREATE TABLE IF NOT EXISTS users (
    id varchar(255) not null,
    name varchar(255),
    provider boolean,
    primary key (id)
);

insert into users values ( '68835901-12e2-46df-bd41-b4f1f0536f69', 'Provider User', TRUE);
insert into users values ( '592c2a09-137a-4565-bc0f-0e151f3d3733', 'Provider User 2', TRUE);
insert into users values ( 'ebaff3de-6fe6-4dc9-b8cf-a584faf6a916', 'Client User', FALSE);
