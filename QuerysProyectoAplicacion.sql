drop database voyyvuelvov2;
create database voyyvuelvov2;

use voyyvuelvov2;

SELECT * FROM voyyvuelvov2.customer_entity;
SELECT * FROM voyyvuelvov2.publisher_entity;
SELECT * FROM voyyvuelvov2.trip_entity;
SELECT * FROM voyyvuelvov2.stop_entity;

insert into stop_entity (id, latitude, longitude, name) values ('1','1','1','testParada1');
insert into stop_entity (id, latitude, longitude, name) values ('1','1','1','testParada2');

insert into customer_entity (id, email, name, password, points, rut) values ('1','customer1@email.com', 'customer1', 'customer1', '0','11111111-1');
insert into customer_entity (id, email, name, password, points, rut) values ('2','customer2@email.com', 'customer2', 'customer2', '0','22222222-2');
insert into customer_entity (id, email, name, password, points, rut) values ('3','customer3@email.com', 'customer3', 'customer3', '0','33333333-3');