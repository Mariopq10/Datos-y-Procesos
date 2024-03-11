-- Crear la base de datos "inventario"
CREATE DATABASE inventario;

-- Usar la base de datos "inventario"
USE inventario;

-- Crear la tabla "producto"
CREATE TABLE producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    cantidad INT,
    seccion INT
);

-- Crear la tabla "usuarios"
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    password VARCHAR(255),
    nombre VARCHAR(255),
    apellido VARCHAR(255)
);