drop database voyyvuelvov2;
create database voyyvuelvov2;

use voyyvuelvov2;

SELECT * FROM voyyvuelvov2.stop;
SELECT * FROM voyyvuelvov2.trip;

SELECT * FROM voyyvuelvov2.user_customer;
SELECT * FROM voyyvuelvov2.user_publisher;

SELECT * FROM voyyvuelvov2.trip;
SELECT * FROM voyyvuelvov2.trip_stop_order;

SELECT * FROM voyyvuelvov2.trip_stop_order WHERE trip_id = 1;

INSERT INTO stop (latitude, longitude, name) 
VALUES 
	('1','1','testParada1'),
	('2','2','testParada2');

INSERT INTO user_publisher (password, rut, user_name, email) VALUES ('secure123', '12345678-9', 'Transportes Andes', 'contacto@transportesandes.cl');

INSERT INTO trip (publisher_id)
VALUES (1); 

INSERT INTO trip_stop_order (trip_id, stop_id, stop_order)
VALUES 
    (1, 1, 0),  -- Primera parada: testParada1
    (1, 2, 2);  -- Segunda parada: testParada2
    
    

