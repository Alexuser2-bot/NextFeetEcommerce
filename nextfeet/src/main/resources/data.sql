INSERT INTO categoria (nombre, descripcion, activo) VALUES ('Running', 'Zapatillas para correr y entrenar', TRUE);
INSERT INTO categoria (nombre, descripcion, activo) VALUES ('Urbanas', 'Estilo urbano para el día a día', TRUE);
INSERT INTO categoria (nombre, descripcion, activo) VALUES ('Basketball', 'Zapatillas de alto rendimiento para básquet', TRUE);
INSERT INTO categoria (nombre, descripcion, activo) VALUES ('Casuales', 'Comodidad y diseño clásico', TRUE);

INSERT INTO marca (nombre) VALUES ('Nike');
INSERT INTO marca (nombre) VALUES ('Adidas');
INSERT INTO marca (nombre) VALUES ('Puma');
INSERT INTO marca (nombre) VALUES ('Converse');
INSERT INTO marca (nombre) VALUES ('Skechers');

INSERT INTO descuento (nombre, porcentaje, fecha_inicio, fecha_fin, activo) VALUES ('Cyber Nike', 15, '2026-11-01', '2026-12-31', TRUE);

INSERT INTO zapatilla (sku, nombre, marca, descripcion, materiales, color, precio, imagen_url, id_categoria, activo, fecha_creacion)
VALUES ('NF-RUN-001', 'AirRun Pro', 'Nike', 'Zapatilla ligera con gran amortiguación para largas distancias.', 'Malla transpirable, EVA', 'Negro', 329.90, '/img/airrun-pro.webp', 1, TRUE, '2026-08-01');
INSERT INTO zapatilla (sku, nombre, marca, descripcion, materiales, color, precio, imagen_url, id_categoria, activo, fecha_creacion)
VALUES ('NF-RUN-002', 'UltraBoost Lite', 'Adidas', 'Retorno de energía en cada pisada.', 'Primeknit, Boost', 'Blanco', 459.00, '/img/ultraboost-lite.webp', 1, TRUE, '2026-08-03');
INSERT INTO zapatilla (sku, nombre, marca, descripcion, materiales, color, precio, imagen_url, id_categoria, activo, fecha_creacion)
VALUES ('NF-URB-001', 'Street Classic', 'Puma', 'Diseño urbano versátil.', 'Cuero sintético, goma', 'Gris', 249.50, '/img/street-classic.webp', 2, TRUE, '2026-08-05');
INSERT INTO zapatilla (sku, nombre, marca, descripcion, materiales, color, precio, imagen_url, id_categoria, activo, fecha_creacion)
VALUES ('NF-URB-002', 'Downtown Low', 'Converse', 'Clásica de lona de corte bajo.', 'Lona, goma vulcanizada', 'Rojo', 219.00, '/img/downtown-low.webp', 2, TRUE, '2026-08-07');
INSERT INTO zapatilla (sku, nombre, marca, descripcion, materiales, color, precio, imagen_url, id_categoria, activo, fecha_creacion)
VALUES ('NF-BAS-001', 'CourtKing 2', 'Nike', 'Soporte de tobillo y tracción superior en cancha.', 'Sintético, espuma Zoom', 'Azul', 389.00, '/img/courtking-2.jpg', 3, TRUE, '2026-08-10');
INSERT INTO zapatilla (sku, nombre, marca, descripcion, materiales, color, precio, imagen_url, id_categoria, activo, fecha_creacion)
VALUES ('NF-CAS-001', 'Comfort Walk', 'Skechers', 'Plantilla memory foam para uso diario.', 'Malla, memory foam', 'Azul marino', 199.90, '/img/comfort-walk.webp', 4, TRUE, '2026-08-12');

UPDATE zapatilla SET id_descuento = 1 WHERE sku = 'NF-RUN-001';

INSERT INTO zapatilla_talla (id_zapatilla, talla, stock) VALUES (1, '39', 10);
INSERT INTO zapatilla_talla (id_zapatilla, talla, stock) VALUES (1, '40', 8);
INSERT INTO zapatilla_talla (id_zapatilla, talla, stock) VALUES (1, '41', 5);
INSERT INTO zapatilla_talla (id_zapatilla, talla, stock) VALUES (2, '40', 6);
INSERT INTO zapatilla_talla (id_zapatilla, talla, stock) VALUES (2, '42', 4);
INSERT INTO zapatilla_talla (id_zapatilla, talla, stock) VALUES (3, '38', 7);
INSERT INTO zapatilla_talla (id_zapatilla, talla, stock) VALUES (3, '40', 9);
INSERT INTO zapatilla_talla (id_zapatilla, talla, stock) VALUES (4, '37', 12);
INSERT INTO zapatilla_talla (id_zapatilla, talla, stock) VALUES (4, '39', 12);
INSERT INTO zapatilla_talla (id_zapatilla, talla, stock) VALUES (5, '42', 3);
INSERT INTO zapatilla_talla (id_zapatilla, talla, stock) VALUES (5, '43', 3);
INSERT INTO zapatilla_talla (id_zapatilla, talla, stock) VALUES (6, '40', 15);
INSERT INTO zapatilla_talla (id_zapatilla, talla, stock) VALUES (6, '41', 15);


INSERT INTO usuario (nombres, apellidos, correo, password, telefono, rol, activo, fecha_registro)
VALUES ('Admin', 'NextFeet', 'admin@nextfeet.com', '$2b$10$XyuH/wzZOU7Wp9GOeh7BYepQGjndIwwyZ.H69zuJkM/r2ygF96iMq', '900000001', 'ADMIN', TRUE, '2026-08-01');
INSERT INTO usuario (nombres, apellidos, correo, password, telefono, rol, activo, fecha_registro)
VALUES ('Cliente', 'Prueba', 'cliente@nextfeet.com', '$2b$10$pvtxGlaCeQuYoYtVoxEIgOve08nQVNFQsy59KfdaMsKGrq5G2mAKC', '900000002', 'CLIENTE', TRUE, '2026-08-01');

INSERT INTO direccion (id_usuario, departamento, provincia, distrito, calle, principal) VALUES (2, 'Lima', 'Lima', 'San Miguel', 'Av. Principal 123', TRUE);

-- Ventas de ejemplo para las métricas del panel
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S001', 2, 'Av. Principal 123', '900000002', '2026-06-08 10:15:00', 329.90, 329.90, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (1, 1, 'AirRun Pro', '40', 1, 329.90, 329.90);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (1, 'PENDIENTE', '2026-06-08 10:15:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (1, 'ENTREGADO', '2026-06-08 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S002', 2, 'Av. Principal 123', '900000002', '2026-06-21 16:40:00', 449.40, 449.40, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (2, 3, 'Street Classic', '40', 1, 249.50, 249.50);
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (2, 6, 'Comfort Walk', '40', 1, 199.90, 199.90);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (2, 'PENDIENTE', '2026-06-21 16:40:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (2, 'ENTREGADO', '2026-06-21 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S003', 2, 'Av. Principal 123', '900000002', '2026-07-04 11:05:00', 459.00, 459.00, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (3, 2, 'UltraBoost Lite', '40', 1, 459.00, 459.00);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (3, 'PENDIENTE', '2026-07-04 11:05:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (3, 'ENTREGADO', '2026-07-04 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S004', 2, 'Av. Principal 123', '900000002', '2026-07-18 19:20:00', 389.00, 389.00, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (4, 5, 'CourtKing 2', '42', 1, 389.00, 389.00);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (4, 'PENDIENTE', '2026-07-18 19:20:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (4, 'ENTREGADO', '2026-07-18 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S005', 2, 'Av. Principal 123', '900000002', '2026-07-27 09:30:00', 438.00, 438.00, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (5, 4, 'Downtown Low', '39', 2, 219.00, 438.00);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (5, 'PENDIENTE', '2026-07-27 09:30:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (5, 'ENTREGADO', '2026-07-27 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S006', 2, 'Av. Principal 123', '900000002', '2026-08-06 13:10:00', 548.90, 548.90, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (6, 1, 'AirRun Pro', '40', 1, 329.90, 329.90);
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (6, 4, 'Downtown Low', '39', 1, 219.00, 219.00);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (6, 'PENDIENTE', '2026-08-06 13:10:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (6, 'ENTREGADO', '2026-08-06 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S007', 2, 'Av. Principal 123', '900000002', '2026-08-23 17:45:00', 399.80, 399.80, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (7, 6, 'Comfort Walk', '40', 2, 199.90, 399.80);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (7, 'PENDIENTE', '2026-08-23 17:45:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (7, 'ENTREGADO', '2026-08-23 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S008', 2, 'Av. Principal 123', '900000002', '2026-09-03 12:00:00', 708.50, 708.50, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (8, 2, 'UltraBoost Lite', '40', 1, 459.00, 459.00);
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (8, 3, 'Street Classic', '40', 1, 249.50, 249.50);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (8, 'PENDIENTE', '2026-09-03 12:00:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (8, 'ENTREGADO', '2026-09-03 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S009', 2, 'Av. Principal 123', '900000002', '2026-09-15 15:25:00', 389.00, 389.00, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (9, 5, 'CourtKing 2', '42', 1, 389.00, 389.00);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (9, 'PENDIENTE', '2026-09-15 15:25:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (9, 'ENTREGADO', '2026-09-15 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S010', 2, 'Av. Principal 123', '900000002', '2026-09-28 20:10:00', 659.80, 659.80, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (10, 1, 'AirRun Pro', '40', 2, 329.90, 659.80);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (10, 'PENDIENTE', '2026-09-28 20:10:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (10, 'ENTREGADO', '2026-09-28 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S011', 2, 'Av. Principal 123', '900000002', '2026-10-05 10:50:00', 848.00, 848.00, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (11, 2, 'UltraBoost Lite', '40', 1, 459.00, 459.00);
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (11, 5, 'CourtKing 2', '42', 1, 389.00, 389.00);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (11, 'PENDIENTE', '2026-10-05 10:50:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (11, 'ENTREGADO', '2026-10-05 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S012', 2, 'Av. Principal 123', '900000002', '2026-10-12 14:35:00', 249.50, 249.50, 'ANULADO', 'Cliente cambió de opinión');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (12, 3, 'Street Classic', '40', 1, 249.50, 249.50);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (12, 'PENDIENTE', '2026-10-12 14:35:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (12, 'ANULADO', '2026-10-12 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S013', 2, 'Av. Principal 123', '900000002', '2026-10-19 18:05:00', 529.80, 529.80, 'EN_CAMINO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (13, 1, 'AirRun Pro', '40', 1, 329.90, 329.90);
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (13, 6, 'Comfort Walk', '40', 1, 199.90, 199.90);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (13, 'PENDIENTE', '2026-10-19 18:05:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (13, 'EN_CAMINO', '2026-10-19 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S014', 2, 'Av. Principal 123', '900000002', '2026-10-29 11:30:00', 718.00, 718.00, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (14, 4, 'Downtown Low', '39', 1, 219.00, 219.00);
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (14, 3, 'Street Classic', '40', 2, 249.50, 499.00);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (14, 'PENDIENTE', '2026-10-29 11:30:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (14, 'ENTREGADO', '2026-10-29 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S015', 2, 'Av. Principal 123', '900000002', '2026-11-03 12:20:00', 778.00, 778.00, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (15, 5, 'CourtKing 2', '42', 2, 389.00, 778.00);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (15, 'PENDIENTE', '2026-11-03 12:20:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (15, 'ENTREGADO', '2026-11-03 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S016', 2, 'Av. Principal 123', '900000002', '2026-11-06 16:15:00', 678.00, 678.00, 'EN_PREPARACION', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (16, 2, 'UltraBoost Lite', '40', 1, 459.00, 459.00);
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (16, 4, 'Downtown Low', '39', 1, 219.00, 219.00);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (16, 'PENDIENTE', '2026-11-06 16:15:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (16, 'EN_PREPARACION', '2026-11-06 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S017', 2, 'Av. Principal 123', '900000002', '2026-11-09 09:45:00', 329.90, 329.90, 'PENDIENTE', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (17, 1, 'AirRun Pro', '40', 1, 329.90, 329.90);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (17, 'PENDIENTE', '2026-11-09 09:45:00');

-- Ventas de años anteriores (para ver el gráfico por años y semanas)
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S018', 2, 'Av. Principal 123', '900000002', '2024-03-14 11:20:00', 249.50, 249.50, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (18, 3, 'Street Classic', '40', 1, 249.50, 249.50);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (18, 'PENDIENTE', '2024-03-14 11:20:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (18, 'ENTREGADO', '2024-03-14 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S019', 2, 'Av. Principal 123', '900000002', '2024-06-22 16:05:00', 399.80, 399.80, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (19, 6, 'Comfort Walk', '40', 2, 199.90, 399.80);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (19, 'PENDIENTE', '2024-06-22 16:05:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (19, 'ENTREGADO', '2024-06-22 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S020', 2, 'Av. Principal 123', '900000002', '2024-09-09 10:40:00', 329.90, 329.90, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (20, 1, 'AirRun Pro', '40', 1, 329.90, 329.90);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (20, 'PENDIENTE', '2024-09-09 10:40:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (20, 'ENTREGADO', '2024-09-09 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S021', 2, 'Av. Principal 123', '900000002', '2024-12-18 18:30:00', 678.00, 678.00, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (21, 2, 'UltraBoost Lite', '40', 1, 459.00, 459.00);
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (21, 4, 'Downtown Low', '39', 1, 219.00, 219.00);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (21, 'PENDIENTE', '2024-12-18 18:30:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (21, 'ENTREGADO', '2024-12-18 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S022', 2, 'Av. Principal 123', '900000002', '2025-02-11 12:10:00', 389.00, 389.00, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (22, 5, 'CourtKing 2', '42', 1, 389.00, 389.00);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (22, 'PENDIENTE', '2025-02-11 12:10:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (22, 'ENTREGADO', '2025-02-11 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S023', 2, 'Av. Principal 123', '900000002', '2025-04-05 15:45:00', 579.40, 579.40, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (23, 1, 'AirRun Pro', '40', 1, 329.90, 329.90);
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (23, 3, 'Street Classic', '40', 1, 249.50, 249.50);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (23, 'PENDIENTE', '2025-04-05 15:45:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (23, 'ENTREGADO', '2025-04-05 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S024', 2, 'Av. Principal 123', '900000002', '2025-05-27 09:25:00', 438.00, 438.00, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (24, 4, 'Downtown Low', '39', 2, 219.00, 438.00);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (24, 'PENDIENTE', '2025-05-27 09:25:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (24, 'ENTREGADO', '2025-05-27 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S025', 2, 'Av. Principal 123', '900000002', '2025-07-19 17:00:00', 459.00, 459.00, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (25, 2, 'UltraBoost Lite', '40', 1, 459.00, 459.00);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (25, 'PENDIENTE', '2025-07-19 17:00:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (25, 'ENTREGADO', '2025-07-19 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S026', 2, 'Av. Principal 123', '900000002', '2025-09-03 13:35:00', 449.40, 449.40, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (26, 6, 'Comfort Walk', '40', 1, 199.90, 199.90);
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (26, 3, 'Street Classic', '40', 1, 249.50, 249.50);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (26, 'PENDIENTE', '2025-09-03 13:35:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (26, 'ENTREGADO', '2025-09-03 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S027', 2, 'Av. Principal 123', '900000002', '2025-10-21 19:15:00', 718.90, 718.90, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (27, 5, 'CourtKing 2', '42', 1, 389.00, 389.00);
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (27, 1, 'AirRun Pro', '40', 1, 329.90, 329.90);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (27, 'PENDIENTE', '2025-10-21 19:15:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (27, 'ENTREGADO', '2025-10-21 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S028', 2, 'Av. Principal 123', '900000002', '2025-11-14 11:50:00', 658.90, 658.90, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (28, 2, 'UltraBoost Lite', '40', 1, 459.00, 459.00);
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (28, 6, 'Comfort Walk', '40', 1, 199.90, 199.90);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (28, 'PENDIENTE', '2025-11-14 11:50:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (28, 'ENTREGADO', '2025-11-14 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S029', 2, 'Av. Principal 123', '900000002', '2025-12-23 20:05:00', 659.80, 659.80, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (29, 1, 'AirRun Pro', '40', 2, 329.90, 659.80);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (29, 'PENDIENTE', '2025-12-23 20:05:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (29, 'ENTREGADO', '2025-12-23 23:00:00');

-- Ventas de los primeros meses de 2026
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S030', 2, 'Av. Principal 123', '900000002', '2026-01-17 12:30:00', 249.50, 249.50, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (30, 3, 'Street Classic', '40', 1, 249.50, 249.50);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (30, 'PENDIENTE', '2026-01-17 12:30:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (30, 'ENTREGADO', '2026-01-17 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S031', 2, 'Av. Principal 123', '900000002', '2026-02-12 16:20:00', 588.90, 588.90, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (31, 5, 'CourtKing 2', '42', 1, 389.00, 389.00);
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (31, 6, 'Comfort Walk', '40', 1, 199.90, 199.90);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (31, 'PENDIENTE', '2026-02-12 16:20:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (31, 'ENTREGADO', '2026-02-12 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S032', 2, 'Av. Principal 123', '900000002', '2026-03-08 10:10:00', 329.90, 329.90, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (32, 1, 'AirRun Pro', '40', 1, 329.90, 329.90);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (32, 'PENDIENTE', '2026-03-08 10:10:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (32, 'ENTREGADO', '2026-03-08 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S033', 2, 'Av. Principal 123', '900000002', '2026-03-26 18:45:00', 438.00, 438.00, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (33, 4, 'Downtown Low', '39', 2, 219.00, 438.00);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (33, 'PENDIENTE', '2026-03-26 18:45:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (33, 'ENTREGADO', '2026-03-26 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S034', 2, 'Av. Principal 123', '900000002', '2026-04-15 11:55:00', 459.00, 459.00, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (34, 2, 'UltraBoost Lite', '40', 1, 459.00, 459.00);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (34, 'PENDIENTE', '2026-04-15 11:55:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (34, 'ENTREGADO', '2026-04-15 23:00:00');
INSERT INTO pedido (codigo, id_usuario, direccion, telefono, fecha, subtotal, total, estado, motivo_anulacion) VALUES ('NF-S035', 2, 'Av. Principal 123', '900000002', '2026-05-09 14:25:00', 579.40, 579.40, 'ENTREGADO', '');
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (35, 1, 'AirRun Pro', '40', 1, 329.90, 329.90);
INSERT INTO detalle_pedido (id_pedido, id_zapatilla, nombre_zapatilla, talla, cantidad, precio_unitario, subtotal) VALUES (35, 3, 'Street Classic', '40', 1, 249.50, 249.50);
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (35, 'PENDIENTE', '2026-05-09 14:25:00');
INSERT INTO pedido_historial (id_pedido, estado, fecha) VALUES (35, 'ENTREGADO', '2026-05-09 23:00:00');
