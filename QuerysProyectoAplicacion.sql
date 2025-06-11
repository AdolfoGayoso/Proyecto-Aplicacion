drop database voyyvuelvov2;
create database voyyvuelvov2;

SELECT * FROM voyyvuelvov2.stop;
SELECT * FROM voyyvuelvov2.trip;

SELECT * FROM voyyvuelvov2.users;

SELECT * FROM voyyvuelvov2.trip;
SELECT * FROM voyyvuelvov2.stop;
SELECT * FROM voyyvuelvov2.trip;
SELECT * FROM voyyvuelvov2.trip_stop_order;

INSERT INTO voyyvuelvov2.users (user_type,password, rut, user_name, email) VALUES
('PUBLISHER', '$2a$10$avYGFvGVuhn1uaek5.AXyOLuCJcRcW2Zcq283HYrqnIUOD0UNwkue', '11.111.111-1', 'nombrePublicador1', 'nombrePublicador1@voyyvuelvo.com'),
('PUBLISHER', '$2a$10$avYGFvGVuhn1uaek5.AXyOLuCJcRcW2Zcq283HYrqnIUOD0UNwkue', '11.111.111-2', 'nombrePublicador2', 'nombrePublicador2@voyyvuelvo.com'),
('PUBLISHER', '$2a$10$avYGFvGVuhn1uaek5.AXyOLuCJcRcW2Zcq283HYrqnIUOD0UNwkue', '11.111.111-3', 'nombrePublicador3', 'nombrePublicador3@voyyvuelvo.com'),
('PUBLISHER', '$2a$10$avYGFvGVuhn1uaek5.AXyOLuCJcRcW2Zcq283HYrqnIUOD0UNwkue', '11.111.111-4', 'nombrePublicador4', 'nombrePublicador4@voyyvuelvo.com'),
('PUBLISHER', '$2a$10$avYGFvGVuhn1uaek5.AXyOLuCJcRcW2Zcq283HYrqnIUOD0UNwkue', '11.111.111-5', 'nombrePublicador5', 'nombrePublicador5@voyyvuelvo.com')
;			 # contraseñapublisher

INSERT INTO voyyvuelvov2.users (user_type, email_verified, password, rut, user_name, email, points) VALUES
('CUSTOMER', true, '$2a$10$avYGFvGVuhn1uaek5.AXyOLuCJcRcW2Zcq283HYrqnIUOD0UNwkue', '21.111.111-1', 'usuarioRegistrado1', 'usuario1@gmail.com', 0),
('CUSTOMER', true, '$2a$10$avYGFvGVuhn1uaek5.AXyOLuCJcRcW2Zcq283HYrqnIUOD0UNwkue', '21.111.111-2', 'usuarioRegistrado2', 'usuario2@gmail.com', 0),
('CUSTOMER', true, '$2a$10$avYGFvGVuhn1uaek5.AXyOLuCJcRcW2Zcq283HYrqnIUOD0UNwkue', '21.111.111-3', 'usuarioRegistrado3', 'usuario3@gmail.com', 0),
('CUSTOMER', true, '$2a$10$avYGFvGVuhn1uaek5.AXyOLuCJcRcW2Zcq283HYrqnIUOD0UNwkue', '21.111.111-4', 'usuarioRegistrado4', 'usuario4@gmail.com', 0),
('CUSTOMER', true, '$2a$10$avYGFvGVuhn1uaek5.AXyOLuCJcRcW2Zcq283HYrqnIUOD0UNwkue', '21.111.111-5', 'usuarioRegistrado5', 'usuario5@gmail.com', 0)
;			 # contraseñapublisher


INSERT INTO voyyvuelvov2.stop (id, name, latitude, longitude) VALUES
(1, 'Angol', -37.8025, -72.7161),
(2, 'Carahue', -38.7058, -73.1658),
(3, 'Cholchol', -38.6000, -72.8500),
(4, 'Collipulli', -37.9500, -72.4333),
(5, 'Cunco', -38.9333, -72.0333),
(6, 'Curacautín', -38.4333, -71.8833),
(7, 'Curarrehue', -39.3500, -71.5833),
(8, 'Ercilla', -38.0500, -72.3833),
(9, 'Freire', -38.9500, -72.6333),
(10, 'Galvarino', -38.4000, -72.7833),
(11, 'Gorbea', -39.1000, -72.6833),
(12, 'Lautaro', -38.5167, -72.4500),
(13, 'Loncoche', -39.3833, -72.6333),
(14, 'Lonquimay', -38.4333, -71.2333),
(15, 'Los Sauces', -37.9667, -72.8333),
(16, 'Lumaco', -38.1500, -72.9000),
(17, 'Melipeuco', -38.8500, -71.6833),
(18, 'Nueva Imperial', -38.7500, -72.9500),
(19, 'Padre Las Casas', -38.7667, -72.6000),
(20, 'Perquenco', -38.4167, -72.3833),
(21, 'Pitrufquén', -38.9833, -72.6500),
(22, 'Pucón', -39.2667, -71.9667),
(23, 'Puerto Saavedra', -38.7833, -73.3833),
(24, 'Purén', -38.0333, -73.0667),
(25, 'Renaico', -37.6667, -72.5833),
(26, 'Teodoro Schmidt', -38.9667, -73.0500),
(27, 'Temuco', -38.7399, -72.5901),
(28, 'Toltén', -39.2167, -73.2167),
(29, 'Traiguén', -38.2500, -72.6833),
(30, 'Victoria', -38.2333, -72.3500),
(31, 'Vilcún', -38.6500, -72.2333),
(32, 'Villarrica', -39.2786, -72.2258);

INSERT INTO voyyvuelvov2.trip (id, active, num_total_seats, plate_number, price, departure_date, departure_time, publisher_id) VALUES
(1, true, 10, 'abcde', 1234, '2025-06-27', '18:00:00', 1)
;
INSERT INTO voyyvuelvov2.trip_stop_order (trip_id, stop_id, stop_order) VALUES
(1, 1, 1),(1, 2, 2),(1, 3, 3),(1, 4, 4),(1, 5, 5)
;

-- Viaje 2: desde Temuco a Villarrica
INSERT INTO voyyvuelvov2.trip (id, active, num_total_seats, plate_number, price, departure_date, departure_time, publisher_id) VALUES
(2, true, 20, 'BBBB1', 3000, '2025-06-27', '07:30:00', 1);

-- Viaje 3: desde Curacautín a Loncoche
INSERT INTO voyyvuelvov2.trip (id, active, num_total_seats, plate_number, price, departure_date, departure_time, publisher_id) VALUES
(3, true, 15, 'CCCC2', 4000, '2025-06-27', '12:00:00', 1);

-- Viaje 4: desde Angol a Traiguén (otro día)
INSERT INTO voyyvuelvov2.trip (id, active, num_total_seats, plate_number, price, departure_date, departure_time, publisher_id) VALUES
(4, true, 18, 'DDDD3', 3500, '2025-06-28', '10:15:00', 1);

-- Viaje 5: desde Pucón a Temuco (más tarde)
INSERT INTO voyyvuelvov2.trip (id, active, num_total_seats, plate_number, price, departure_date, departure_time, publisher_id) VALUES
(5, true, 25, 'EEEE4', 5000, '2025-06-27', '19:45:00', 1);

-- Viaje 6: desde Perquenco a Villarrica
INSERT INTO voyyvuelvov2.trip (id, active, num_total_seats, plate_number, price, departure_date, departure_time, publisher_id) VALUES
(6, true, 20, 'BBBB1', 3000, '2025-06-27', '07:30:00', 1);

-- Trip 2: Temuco (27) → Padre Las Casas (19) → Freire (9) → Villarrica (32)
INSERT INTO voyyvuelvov2.trip_stop_order (trip_id, stop_id, stop_order) VALUES
(2, 27, 1),
(2, 19, 2),
(2, 9, 3),
(2, 32, 4);

-- Trip 3: Curacautín (6) → Lautaro (12) → Vilcún (31) → Loncoche (13)
INSERT INTO voyyvuelvov2.trip_stop_order (trip_id, stop_id, stop_order) VALUES
(3, 6, 1),
(3, 12, 2),
(3, 31, 3),
(3, 13, 4);

-- Trip 4: Angol (1) → Collipulli (4) → Victoria (30) → Traiguén (29)
INSERT INTO voyyvuelvov2.trip_stop_order (trip_id, stop_id, stop_order) VALUES
(4, 1, 1),
(4, 4, 2),
(4, 30, 3),
(4, 29, 4);

-- Trip 5: Pucón (22) → Villarrica (32) → Temuco (27)
INSERT INTO voyyvuelvov2.trip_stop_order (trip_id, stop_id, stop_order) VALUES
(5, 22, 1),
(5, 32, 2),
(5, 27, 3);

-- Trip 6: Temuco (27) → Padre Las Casas (19) → Freire (9) → Villarrica (32)
INSERT INTO voyyvuelvov2.trip_stop_order (trip_id, stop_id, stop_order) VALUES
(6, 20, 1),
(6, 27, 2),
(6, 19, 3),
(6, 9, 4),
(6, 32, 5);


    
