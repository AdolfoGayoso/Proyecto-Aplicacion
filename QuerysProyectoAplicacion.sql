drop database voyyvuelvov2;
create database voyyvuelvov2;

use voyyvuelvov2;

SELECT * FROM voyyvuelvov2.stop;
SELECT * FROM voyyvuelvov2.trip;

SELECT * FROM voyyvuelvov2.users;

SELECT * FROM voyyvuelvov2.trip;
SELECT * FROM voyyvuelvov2.stop;
SELECT * FROM voyyvuelvov2.trip;
SELECT * FROM voyyvuelvov2.trip_stop_order;

INSERT INTO voyyvuelvov2.users (id, user_type, password, rut, user_name, email) VALUES
(1, 'PUBLISHER', '$2a$10$avYGFvGVuhn1uaek5.AXyOLuCJcRcW2Zcq283HYrqnIUOD0UNwkue', '11.111.111-1', 'nombrePublicador1', 'nombrePublicador1@voyyvuelvo.com'),
(2, 'PUBLISHER', '$2a$10$avYGFvGVuhn1uaek5.AXyOLuCJcRcW2Zcq283HYrqnIUOD0UNwkue', '11.111.111-2', 'nombrePublicador2', 'nombrePublicador2@voyyvuelvo.com'),
(3, 'PUBLISHER', '$2a$10$avYGFvGVuhn1uaek5.AXyOLuCJcRcW2Zcq283HYrqnIUOD0UNwkue', '11.111.111-3', 'nombrePublicador3', 'nombrePublicador3@voyyvuelvo.com'),
(4, 'PUBLISHER', '$2a$10$avYGFvGVuhn1uaek5.AXyOLuCJcRcW2Zcq283HYrqnIUOD0UNwkue', '11.111.111-4', 'nombrePublicador4', 'nombrePublicador4@voyyvuelvo.com'),
(5, 'PUBLISHER', '$2a$10$avYGFvGVuhn1uaek5.AXyOLuCJcRcW2Zcq283HYrqnIUOD0UNwkue', '11.111.111-5', 'nombrePublicador5', 'nombrePublicador5@voyyvuelvo.com')
;				# contraseñapublisher

INSERT INTO voyyvuelvov2.trip (id, active, num_seats, plate_number, price, departure_date, departure_time, publisher_id) VALUES
(1, True, 34, 'LO 4627 ZK', 4483, '2025-06-27', '18:00:00', 3),
(2, True, 19, 'AB 8773 EH', 2794, '2025-06-03', '15:45:00', 4),
(3, True, 37, '9578 XGW', 1367, '2025-06-11', '10:15:00', 4),
(4, True, 23, 'SS 3468 CA', 1099, '2025-06-16', '13:15:00', 4),
(5, True, 20, 'OU 4841 OJ', 4739, '2025-05-31', '14:15:00', 2),
(6, True, 35, 'LE 2083 PG', 1110, '2025-06-07', '13:30:00', 2),
(7, True, 37, 'GI 2619 SO', 4305, '2025-06-26', '12:00:00', 1),
(8, True, 27, 'CO 5506 GP', 1893, '2025-06-12', '20:45:00', 5),
(9, True, 36, '9279 LGH', 2040, '2025-06-02', '09:00:00', 2),
(10, True, 13, 'M 1287 MI', 1455, '2025-06-05', '20:15:00', 1),
(11, True, 49, '1719 NBW', 4044, '2025-06-02', '13:00:00', 3),
(12, True, 48, 'O 5956 AL', 3997, '2025-06-17', '09:45:00', 4),
(13, True, 38, '3191 RKB', 1490, '2025-06-22', '09:15:00', 5),
(14, True, 37, 'GE 3075 ZW', 2752, '2025-06-14', '10:00:00', 3),
(15, True, 32, 'SG 8424 FN', 1689, '2025-06-15', '13:15:00', 5),
(16, True, 16, 'ML 0088 JV', 1098, '2025-06-15', '14:00:00', 3),
(17, True, 32, '3705 GNN', 1097, '2025-05-29', '17:30:00', 2),
(18, True, 12, 'MU 8750 JX', 3645, '2025-06-19', '19:30:00', 2),
(19, True, 22, 'SA 4269 EG', 4987, '2025-06-07', '17:00:00', 2),
(20, True, 23, 'CC 2902 SM', 1560, '2025-06-11', '18:30:00', 2);

INSERT INTO trip_stop_order (id, trip_id, stop_id, stop_order) VALUES
(1, 1, 14, 1), (2, 1, 16, 2), (3, 1, 10, 3), (4, 1, 5, 4), (5, 1, 2, 5),
(6, 2, 14, 1), (7, 2, 10, 2), (8, 2, 18, 3), (9, 2, 24, 4), (10, 2, 28, 5), (11, 2, 1, 6),
(12, 3, 10, 1), (13, 3, 27, 2), (14, 3, 22, 3), (15, 3, 9, 4), (16, 3, 24, 5), (17, 3, 3, 6),
(18, 4, 6, 1), (19, 4, 14, 2), (20, 4, 16, 3),
(21, 5, 13, 1), (22, 5, 14, 2), (23, 5, 21, 3),
(24, 6, 26, 1), (25, 6, 22, 2), (26, 6, 7, 3), (27, 6, 3, 4), (28, 6, 25, 5),
(29, 7, 1, 1), (30, 7, 29, 2), (31, 7, 26, 3), (32, 7, 9, 4), (33, 7, 32, 5),
(34, 8, 7, 1), (35, 8, 31, 2), (36, 8, 2, 3), (37, 8, 26, 4), (38, 8, 18, 5), (39, 8, 23, 6),
(40, 9, 18, 1), (41, 9, 29, 2), (42, 9, 22, 3), (43, 9, 11, 4),
(44, 10, 22, 1), (45, 10, 10, 2), (46, 10, 8, 3), (47, 10, 30, 4), (48, 10, 1, 5), (49, 10, 9, 6),
(50, 11, 18, 1), (51, 11, 3, 2), (52, 11, 17, 3), (53, 11, 9, 4), (54, 11, 29, 5), (55, 11, 31, 6), (56, 11, 6, 7),
(57, 12, 21, 1), (58, 12, 6, 2), (59, 12, 26, 3), (60, 12, 30, 4), (61, 12, 7, 5),
(62, 13, 31, 1), (63, 13, 11, 2), (64, 13, 4, 3), (65, 13, 10, 4), (66, 13, 13, 5), (67, 13, 3, 6),
(68, 14, 9, 1), (69, 14, 3, 2), (70, 14, 12, 3), (71, 14, 18, 4),
(72, 15, 26, 1), (73, 15, 22, 2), (74, 15, 21, 3),
(75, 16, 21, 1), (76, 16, 30, 2), (77, 16, 10, 3),
(78, 17, 13, 1), (79, 17, 7, 2), (80, 17, 5, 3), (81, 17, 8, 4), (82, 17, 28, 5), (83, 17, 31, 6), (84, 17, 3, 7),
(85, 18, 22, 1), (86, 18, 6, 2), (87, 18, 1, 3), (88, 18, 3, 4), (89, 18, 21, 5), (90, 18, 13, 6), (91, 18, 8, 7),
(92, 19, 2, 1), (93, 19, 15, 2), (94, 19, 8, 3), (95, 19, 13, 4), (96, 19, 12, 5), (97, 19, 7, 6),
(98, 20, 19, 1), (99, 20, 21, 2), (100, 20, 8, 3), (101, 20, 1, 4), (102, 20, 25, 5), (103, 20, 15, 6), (104, 20, 24, 7);
                  
INSERT INTO voyyvuelvov2.stop (name, latitude, longitude) VALUES
('Angol', -37.8025, -72.7161),
('Carahue', -38.7058, -73.1658),
('Cholchol', -38.6000, -72.8500),
('Collipulli', -37.9500, -72.4333),
('Cunco', -38.9333, -72.0333),
('Curacautín', -38.4333, -71.8833),
('Curarrehue', -39.3500, -71.5833),
('Ercilla', -38.0500, -72.3833),
('Freire', -38.9500, -72.6333),
('Galvarino', -38.4000, -72.7833),
('Gorbea', -39.1000, -72.6833),
('Lautaro', -38.5167, -72.4500),
('Loncoche', -39.3833, -72.6333),
('Lonquimay', -38.4333, -71.2333),
('Los Sauces', -37.9667, -72.8333),
('Lumaco', -38.1500, -72.9000),
('Melipeuco', -38.8500, -71.6833),
('Nueva Imperial', -38.7500, -72.9500),
('Padre Las Casas', -38.7667, -72.6000),
('Perquenco', -38.4167, -72.3833),
('Pitrufquén', -38.9833, -72.6500),
('Pucón', -39.2667, -71.9667),
('Puerto Saavedra', -38.7833, -73.3833),
('Purén', -38.0333, -73.0667),
('Renaico', -37.6667, -72.5833),
('Teodoro Schmidt', -38.9667, -73.0500),
('Temuco', -38.7399, -72.5901),
('Toltén', -39.2167, -73.2167),
('Traiguén', -38.2500, -72.6833),
('Victoria', -38.2333, -72.3500),
('Vilcún', -38.6500, -72.2333),
('Villarrica', -39.2786, -72.2258);