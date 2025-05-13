drop database voyyvuelvov2;
create database voyyvuelvov2;

use voyyvuelvov2;

SELECT * FROM voyyvuelvov2.stop;
SELECT * FROM voyyvuelvov2.trip;

SELECT * FROM voyyvuelvov2.users;

SELECT * FROM voyyvuelvov2.trip;
SELECT * FROM voyyvuelvov2.trip_stop_order;

INSERT INTO stop (latitude, longitude, name) 
VALUES 
	('1','1','testParada1'),
	('2','2','testParada2');
    
INSERT INTO users (
    user_type, 
    password, 
    rut, 
    user_name, 
    email
) VALUES (
    'PUBLISHER', 
    '$2a$10$13qsom4dlatPdRUm06LHd.e614IkbUT4xPnhEj6Hvdq1nLTaZDXGS', 
    '12345678-9', 
    'nombrePublicador', 
    'publicador@example.com'
);

