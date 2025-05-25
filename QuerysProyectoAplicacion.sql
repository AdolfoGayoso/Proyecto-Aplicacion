drop database voyyvuelvov2;
create database voyyvuelvov2;

use voyyvuelvov2;

SELECT * FROM voyyvuelvov2.stop;
SELECT * FROM voyyvuelvov2.trip;

SELECT * FROM voyyvuelvov2.users;

SELECT * FROM voyyvuelvov2.trip;
SELECT * FROM voyyvuelvov2.stop;
SELECT * FROM voyyvuelvov2.trip_stop_order;

INSERT INTO users (
    user_type, 
    password, 
    rut, 
    user_name, 
    email
) VALUES (
    'PUBLISHER', 
    '$2a$10$avYGFvGVuhn1uaek5.AXyOLuCJcRcW2Zcq283HYrqnIUOD0UNwkue', # contraseñapublisher
    '12345678-9', 
    'nombrePublicador', 
    'publicador@example.com'
);

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
