CREATE TABLE IF NOT EXISTS usuario (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    rol VARCHAR(20) NOT NULL,
    activo BOOLEAN DEFAULT TRUE NOT NULL,
    fecha_registro DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS categoria (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(300),
    activo BOOLEAN DEFAULT TRUE NOT NULL
);

CREATE TABLE IF NOT EXISTS marca (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS descuento (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    porcentaje DECIMAL(5,2) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    activo BOOLEAN DEFAULT TRUE NOT NULL
);

CREATE TABLE IF NOT EXISTS zapatilla (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    sku VARCHAR(30) NOT NULL UNIQUE,
    nombre VARCHAR(150) NOT NULL,
    marca VARCHAR(80) NOT NULL,
    descripcion VARCHAR(1000),
    materiales VARCHAR(300),
    color VARCHAR(80),
    precio DECIMAL(10,2) NOT NULL,
    imagen_url VARCHAR(300),
    id_categoria INT NOT NULL,
    activo BOOLEAN DEFAULT TRUE NOT NULL,
    fecha_creacion DATE NOT NULL,
    id_descuento INT,
    CONSTRAINT fk_zapatilla_categoria FOREIGN KEY (id_categoria) REFERENCES categoria(id),
    CONSTRAINT fk_zapatilla_descuento FOREIGN KEY (id_descuento) REFERENCES descuento(id)
);

CREATE TABLE IF NOT EXISTS zapatilla_talla (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_zapatilla INT NOT NULL,
    talla VARCHAR(10) NOT NULL,
    stock INT DEFAULT 0 NOT NULL,
    CONSTRAINT uq_zapatilla_talla UNIQUE (id_zapatilla, talla),
    CONSTRAINT fk_talla_zapatilla FOREIGN KEY (id_zapatilla) REFERENCES zapatilla(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS historial_stock (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_zapatilla INT NOT NULL,
    talla VARCHAR(10) NOT NULL,
    cantidad_anterior INT NOT NULL,
    cantidad_nueva INT NOT NULL,
    motivo VARCHAR(100),
    fecha TIMESTAMP NOT NULL,
    CONSTRAINT fk_hist_zapatilla FOREIGN KEY (id_zapatilla) REFERENCES zapatilla(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS carrito (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario INT NOT NULL UNIQUE,
    fecha_actualizacion TIMESTAMP NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);

CREATE TABLE IF NOT EXISTS carrito_item (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_carrito INT NOT NULL,
    id_zapatilla INT NOT NULL,
    talla VARCHAR(10) NOT NULL,
    cantidad INT NOT NULL,
    CONSTRAINT uq_carrito_item UNIQUE (id_carrito, id_zapatilla, talla),
    FOREIGN KEY (id_carrito) REFERENCES carrito(id) ON DELETE CASCADE,
    FOREIGN KEY (id_zapatilla) REFERENCES zapatilla(id)
);

CREATE TABLE IF NOT EXISTS pedido (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    id_usuario INT NOT NULL,
    direccion VARCHAR(240) NOT NULL,
    telefono VARCHAR(9) NOT NULL,
    fecha TIMESTAMP NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    descuento DECIMAL(10,2) DEFAULT 0 NOT NULL,
    envio DECIMAL(10,2) DEFAULT 0 NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    motivo_anulacion VARCHAR(300),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);

CREATE TABLE IF NOT EXISTS detalle_pedido (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_zapatilla INT NOT NULL,
    nombre_zapatilla VARCHAR(150) NOT NULL,
    talla VARCHAR(10) NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedido(id) ON DELETE CASCADE,
    FOREIGN KEY (id_zapatilla) REFERENCES zapatilla(id)
);

CREATE TABLE IF NOT EXISTS pedido_historial (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_pedido INT NOT NULL,
    estado VARCHAR(20) NOT NULL,
    fecha TIMESTAMP NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedido(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS direccion (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario INT NOT NULL,
    departamento VARCHAR(60) NOT NULL,
    provincia VARCHAR(60) NOT NULL,
    distrito VARCHAR(60) NOT NULL,
    calle VARCHAR(120) NOT NULL,
    principal BOOLEAN DEFAULT FALSE NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);
