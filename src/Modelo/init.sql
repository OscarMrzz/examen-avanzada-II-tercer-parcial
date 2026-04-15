-- Base de datos para Sistema de Gestión de Decoraciones
-- Creación de tablas normalizadas con relaciones FK

-- Crear base de datos
DROP DATABASE IF EXISTS db_decoraciones;
CREATE DATABASE IF NOT EXISTS db_decoraciones;
USE db_decoraciones;

-- Tabla Usuarios
CREATE TABLE usuarios (
    id_usuario VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    nombre_usuario VARCHAR(100) NOT NULL,
    user_usuario VARCHAR(50) UNIQUE NOT NULL,
    pass_usuario VARCHAR(255) NOT NULL,
    privilegio_usuario ENUM('ADMIN', 'VENTAS', 'INVENTARIO') NOT NULL DEFAULT 'VENTAS',
    foto_usuario VARCHAR(255),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado_usuario BOOLEAN DEFAULT TRUE
);

-- Tabla Clientes
CREATE TABLE clientes (
    id_cliente VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    nombre_cliente VARCHAR(100) NOT NULL,
    rtn_cliente VARCHAR(20) UNIQUE,
    tipo_cliente ENUM('Consumidor Final', 'Empresa') NOT NULL DEFAULT 'Consumidor Final',
    telefono_cliente VARCHAR(20),
    email_cliente VARCHAR(100),
    direccion_cliente TEXT,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado_cliente BOOLEAN DEFAULT TRUE
);

-- Tabla Proveedores
CREATE TABLE proveedores (
    id_proveedor VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    nombre_proveedor VARCHAR(100) NOT NULL,
    rtn_proveedor VARCHAR(20) UNIQUE,
    telefono_proveedor VARCHAR(20) NOT NULL,
    email_proveedor VARCHAR(100),
    direccion_proveedor TEXT,
    contacto_proveedor VARCHAR(100),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado_proveedor BOOLEAN DEFAULT TRUE
);

-- Tabla Colecciones
CREATE TABLE colecciones (
    id_coleccion VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    nombre_coleccion VARCHAR(150) NOT NULL,
    disenador_coleccion VARCHAR(100) NOT NULL,
    num_coleccion_coleccion VARCHAR(50) NOT NULL,
    anio_coleccion INT NOT NULL,
    descripcion_coleccion TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado_coleccion BOOLEAN DEFAULT TRUE,
    CONSTRAINT chk_anio_coleccion CHECK (anio_coleccion > 1900 AND anio_coleccion <= 2025),
    CONSTRAINT chk_num_coleccion UNIQUE (num_coleccion_coleccion)
);

-- Tabla Decoraciones
CREATE TABLE decoraciones (
    id_decoracion VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    nombre_decoracion VARCHAR(150) NOT NULL,
    stock_decoracion INT NOT NULL DEFAULT 0,
    stock_minimo_decoracion INT NOT NULL DEFAULT 5,
    stock_maximo_decoracion INT NOT NULL DEFAULT 100,
    id_proveedor_decoracion VARCHAR(36) NOT NULL,
    imagen_decoracion VARCHAR(255),
    id_coleccion_decoracion VARCHAR(36),
    descripcion_decoracion TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado_decoracion BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_proveedor_decoracion) REFERENCES proveedores(id_proveedor),
    FOREIGN KEY (id_coleccion_decoracion) REFERENCES colecciones(id_coleccion),
    CONSTRAINT chk_stock CHECK (stock_decoracion >= 0)
);

-- Tabla Facturas de Compra
CREATE TABLE facturas_compra (
    id_factura_compra VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    id_proveedor_factura_compra VARCHAR(36) NOT NULL,
    numero_factura VARCHAR(50) UNIQUE NOT NULL,
    total_factura_compra DECIMAL(12,2) NOT NULL,
    tipo_pago_factura_compra ENUM('Contado', 'Crédito') NOT NULL,
    estado_factura_compra ENUM('Pagada', 'Pendiente') NOT NULL DEFAULT 'Pendiente',
    fecha_factura_compra DATE NOT NULL,
    fecha_vencimiento_factura DATE,
    condicion_factura TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_proveedor_factura_compra) REFERENCES proveedores(id_proveedor)
);

-- Tabla Detalle de Compra
CREATE TABLE detalle_compra (
    id_detalle_compra VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    id_factura_compra_detalle VARCHAR(36) NOT NULL,
    id_decoracion_detalle VARCHAR(36) NOT NULL,
    cantidad_detalle_compra INT NOT NULL,
    precio_costo_compra DECIMAL(10,2) NOT NULL,
    precio_venta_compra DECIMAL(10,2) NOT NULL,
    subtotal_detalle_compra DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_factura_compra_detalle) REFERENCES facturas_compra(id_factura_compra),
    FOREIGN KEY (id_decoracion_detalle) REFERENCES decoraciones(id_decoracion),
    CONSTRAINT chk_cantidad_compra CHECK (cantidad_detalle_compra > 0),
    CONSTRAINT chk_precio_venta_compra CHECK (precio_venta_compra > precio_costo_compra)
);

-- Tabla Ventas
CREATE TABLE ventas (
    id_venta VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    id_cliente_venta VARCHAR(36) NOT NULL,
    numero_factura_venta VARCHAR(50) UNIQUE NOT NULL,
    subtotal_venta DECIMAL(12,2) NOT NULL,
    impuesto_venta DECIMAL(10,2) NOT NULL,
    descuento_venta DECIMAL(10,2) DEFAULT 0,
    total_venta DECIMAL(12,2) NOT NULL,
    tipo_pago_venta ENUM('Efectivo', 'Tarjeta', 'Mixto') NOT NULL DEFAULT 'Efectivo',
    monto_efectivo DECIMAL(12,2),
    monto_tarjeta DECIMAL(12,2),
    cambio_venta DECIMAL(10,2) DEFAULT 0,
    fecha_venta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado_venta ENUM('Activa', 'Cancelada') DEFAULT 'Activa',
    id_usuario_vendedor VARCHAR(36),
    FOREIGN KEY (id_cliente_venta) REFERENCES clientes(id_cliente),
    FOREIGN KEY (id_usuario_vendedor) REFERENCES usuarios(id_usuario),
    CONSTRAINT chk_total_venta CHECK (total_venta >= 0)
);

-- Tabla Detalle de Ventas
CREATE TABLE detalle_venta (
    id_detalle_venta VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    id_venta_detalle VARCHAR(36) NOT NULL,
    id_decoracion_detalle VARCHAR(36) NOT NULL,
    cantidad_detalle_venta INT NOT NULL,
    precio_unitario_venta DECIMAL(10,2) NOT NULL,
    subtotal_detalle_venta DECIMAL(10,2) NOT NULL,
    descuento_detalle DECIMAL(10,2) DEFAULT 0,
    FOREIGN KEY (id_venta_detalle) REFERENCES ventas(id_venta),
    FOREIGN KEY (id_decoracion_detalle) REFERENCES decoraciones(id_decoracion),
    CONSTRAINT chk_cantidad_venta CHECK (cantidad_detalle_venta > 0)
);

-- Tabla Recibos de Pago (para proveedores)
CREATE TABLE recibos_pago (
    id_recibo_pago VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    id_factura_compra_pago VARCHAR(36) NOT NULL,
    id_proveedor_pago VARCHAR(36) NOT NULL,
    monto_pago DECIMAL(12,2) NOT NULL,
    fecha_pago DATE NOT NULL,
    metodo_pago ENUM('Efectivo', 'Transferencia', 'Cheque') NOT NULL,
    referencia_pago VARCHAR(100),
    observaciones_pago TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_factura_compra_pago) REFERENCES facturas_compra(id_factura_compra),
    FOREIGN KEY (id_proveedor_pago) REFERENCES proveedores(id_proveedor)
);


-- ========================================
-- VISTAS DETALLADAS PARA REPORTES Y TABLAS
-- ========================================

-- Vista detallada de Ventas con todos los datos relacionados
CREATE VIEW vista_detallada_ventas AS
SELECT 
    v.id_venta,
    v.numero_factura_venta,
    v.fecha_venta,
    c.id_cliente,
    c.nombre_cliente,
    c.rtn_cliente,
    c.tipo_cliente,
    c.telefono_cliente,
    u.id_usuario,
    u.nombre_usuario AS vendedor,
    v.subtotal_venta,
    v.impuesto_venta,
    v.descuento_venta,
    v.total_venta,
    v.tipo_pago_venta,
    v.monto_efectivo,
    v.monto_tarjeta,
    v.cambio_venta,
    v.estado_venta,
    -- Contador de productos en la venta
    (SELECT COUNT(*) FROM detalle_venta dv WHERE dv.id_venta_detalle = v.id_venta) AS cantidad_productos
FROM ventas v
INNER JOIN clientes c ON v.id_cliente_venta = c.id_cliente
LEFT JOIN usuarios u ON v.id_usuario_vendedor = u.id_usuario
WHERE v.estado_venta = 'Activa';

-- Vista detallada de Detalles de Ventas
CREATE VIEW vista_detallada_detalle_ventas AS
SELECT 
    dv.id_detalle_venta,
    dv.id_venta_detalle,
    v.numero_factura_venta,
    v.fecha_venta,
    c.nombre_cliente,
    d.id_decoracion,
    d.nombre_decoracion,
    CASE 
        WHEN d.id_coleccion_decoracion IS NOT NULL THEN TRUE
        ELSE FALSE
    END AS es_coleccion_decoracion,
    co.nombre_coleccion,
    co.disenador_coleccion,
    co.num_coleccion_coleccion,
    co.anio_coleccion,
    p.nombre_proveedor,
    dv.cantidad_detalle_venta,
    dv.precio_unitario_venta,
    dv.subtotal_detalle_venta,
    dv.descuento_detalle,
    -- Precio con descuento incluido
    (dv.precio_unitario_venta - (dv.precio_unitario_venta * (dv.descuento_detalle / 100))) AS precio_final_unitario
FROM detalle_venta dv
INNER JOIN ventas v ON dv.id_venta_detalle = v.id_venta
INNER JOIN clientes c ON v.id_cliente_venta = c.id_cliente
INNER JOIN decoraciones d ON dv.id_decoracion_detalle = d.id_decoracion
INNER JOIN proveedores p ON d.id_proveedor_decoracion = p.id_proveedor
LEFT JOIN colecciones co ON d.id_coleccion_decoracion = co.id_coleccion
WHERE v.estado_venta = 'Activa';

-- Vista detallada de Compras
CREATE VIEW vista_detallada_compras AS
SELECT 
    fc.id_factura_compra,
    fc.numero_factura,
    fc.fecha_factura_compra,
    fc.fecha_vencimiento_factura,
    p.id_proveedor,
    p.nombre_proveedor,
    p.rtn_proveedor,
    p.telefono_proveedor,
    fc.total_factura_compra,
    fc.tipo_pago_factura_compra,
    fc.estado_factura_compra,
    fc.condicion_factura,
    -- Total pagado de esta factura
    COALESCE(SUM(rp.monto_pago), 0) AS total_pagado,
    -- Saldo pendiente
    (fc.total_factura_compra - COALESCE(SUM(rp.monto_pago), 0)) AS saldo_pendiente,
    -- Estado del pago
    CASE 
        WHEN fc.total_factura_compra <= COALESCE(SUM(rp.monto_pago), 0) THEN 'Pagada'
        WHEN fc.fecha_vencimiento_factura < CURDATE() AND fc.estado_factura_compra = 'Pendiente' THEN 'Vencida'
        ELSE fc.estado_factura_compra
    END AS estado_real_pago
FROM facturas_compra fc
INNER JOIN proveedores p ON fc.id_proveedor_factura_compra = p.id_proveedor
LEFT JOIN recibos_pago rp ON fc.id_factura_compra = rp.id_factura_compra_pago
GROUP BY fc.id_factura_compra, p.id_proveedor;

-- Vista detallada de Inventario
CREATE VIEW vista_detallada_inventario AS
SELECT 
    d.id_decoracion,
    d.nombre_decoracion,
    d.stock_decoracion,
    d.stock_minimo_decoracion,
    d.stock_maximo_decoracion,
    -- Obtener precios de la última compra
    COALESCE(dc.precio_costo_compra, 0) AS precio_costo_decoracion,
    COALESCE(dc.precio_venta_compra, 0) AS precio_venta_decoracion,
    CASE 
        WHEN d.id_coleccion_decoracion IS NOT NULL THEN TRUE
        ELSE FALSE
    END AS es_coleccion_decoracion,
    co.nombre_coleccion,
    co.disenador_coleccion,
    co.num_coleccion_coleccion,
    co.anio_coleccion,
    d.descripcion_decoracion,
    d.estado_decoracion,
    p.id_proveedor,
    p.nombre_proveedor,
    p.telefono_proveedor,
    -- Margen de utilidad
    CASE 
        WHEN dc.precio_costo_compra > 0 THEN ((dc.precio_venta_compra - dc.precio_costo_compra) / dc.precio_costo_compra * 100)
        ELSE 0
    END AS margen_utilidad_porcentaje,
    -- Valor total del inventario
    (d.stock_decoracion * COALESCE(dc.precio_costo_compra, 0)) AS valor_total_inventario,
    -- Estado de stock
    CASE 
        WHEN d.stock_decoracion = 0 THEN 'Agotado'
        WHEN d.stock_decoracion <= d.stock_minimo_decoracion THEN 'Stock Mínimo'
        WHEN d.stock_decoracion >= d.stock_maximo_decoracion THEN 'Stock Máximo'
        ELSE 'Normal'
    END AS estado_stock
FROM decoraciones d
INNER JOIN proveedores p ON d.id_proveedor_decoracion = p.id_proveedor
LEFT JOIN colecciones co ON d.id_coleccion_decoracion = co.id_coleccion
LEFT JOIN (
    SELECT 
        id_decoracion_detalle,
        precio_costo_compra,
        precio_venta_compra,
        ROW_NUMBER() OVER (PARTITION BY id_decoracion_detalle ORDER BY fc.fecha_factura_compra DESC) as rn
    FROM detalle_compra dc
    INNER JOIN facturas_compra fc ON dc.id_factura_compra_detalle = fc.id_factura_compra
) dc ON d.id_decoracion = dc.id_decoracion_detalle AND dc.rn = 1
WHERE d.estado_decoracion = TRUE;

-- Vista de decoraciones más vendidas
CREATE VIEW vista_decoraciones_mas_vendidas AS
SELECT 
    d.id_decoracion,
    d.nombre_decoracion,
    CASE 
        WHEN d.id_coleccion_decoracion IS NOT NULL THEN TRUE
        ELSE FALSE
    END AS es_coleccion_decoracion,
    co.nombre_coleccion,
    co.disenador_coleccion,
    co.num_coleccion_coleccion,
    co.anio_coleccion,
    p.nombre_proveedor,
    COALESCE(SUM(dv.cantidad_detalle_venta), 0) AS total_unidades_vendidas,
    COALESCE(SUM(dv.subtotal_detalle_venta), 0) AS total_ventas_generadas,
    COALESCE(COUNT(DISTINCT dv.id_venta_detalle), 0) AS cantidad_ventas
FROM decoraciones d
INNER JOIN proveedores p ON d.id_proveedor_decoracion = p.id_proveedor
LEFT JOIN colecciones co ON d.id_coleccion_decoracion = co.id_coleccion
LEFT JOIN detalle_venta dv ON d.id_decoracion = dv.id_decoracion_detalle
LEFT JOIN ventas v ON dv.id_venta_detalle = v.id_venta AND v.estado_venta = 'Activa'
WHERE d.estado_decoracion = TRUE
GROUP BY d.id_decoracion
ORDER BY total_unidades_vendidas DESC;

-- Vista de reporte de ventas diarias
CREATE VIEW vista_ventas_diarias AS
SELECT 
    DATE(v.fecha_venta) AS fecha_venta,
    COUNT(v.id_venta) AS cantidad_ventas,
    SUM(v.total_venta) AS total_ventas_dia,
    SUM(v.subtotal_venta) AS subtotal_dia,
    SUM(v.impuesto_venta) AS total_impuestos_dia,
    SUM(v.descuento_venta) AS total_descuentos_dia,
    AVG(v.total_venta) AS promedio_venta,
    COUNT(DISTINCT v.id_cliente_venta) AS cantidad_clientes
FROM ventas v
WHERE v.estado_venta = 'Activa'
GROUP BY DATE(v.fecha_venta)
ORDER BY fecha_venta DESC;

-- ========================================
-- DATOS DE EJEMPLO
-- ========================================

-- Insertar usuarios de ejemplo
INSERT INTO usuarios (nombre_usuario, user_usuario, pass_usuario, privilegio_usuario) VALUES
('Administrador', 'ADMIN', 'ADMIN2025', 'ADMIN'),
('Juan Pérez', 'jventas', 'pass123', 'VENTAS'),
('María López', 'minventario', 'pass123', 'INVENTARIO'),
('Carlos Rodríguez', 'cventas2', 'pass123', 'VENTAS');

-- Insertar proveedores de ejemplo
INSERT INTO proveedores (nombre_proveedor, rtn_proveedor, telefono_proveedor, email_proveedor, direccion_proveedor, contacto_proveedor) VALUES
('Decoraciones S.A.', '08011998000123', '2234-5678', 'info@decoraciones.com', 'Colonia Palmira, Tegucigalpa', 'Roberto Silva'),
('Espejos y Lámparas HN', '08011998000456', '2255-8901', 'ventas@espejos.hn', 'Boulevard Morazán, San Pedro Sula', 'Ana Martínez'),
('Muebles del Arte', '08011998000789', '2212-3456', 'contacto@mueblesarte.hn', 'Centro Comercial, Tegucigalpa', 'Luis Gómez'),
('Alfombras Elegantes', '08011998000111', '2234-9999', 'alfombras@elegant.hn', 'Zona Comercial, La Ceiba', 'Carmen Rodríguez');

-- Insertar clientes de ejemplo
INSERT INTO clientes (nombre_cliente, rtn_cliente, tipo_cliente, telefono_cliente, email_cliente, direccion_cliente) VALUES
('Empresa ABC S.A.', '08011999000555', 'Empresa', '2234-1111', 'contacto@abc.hn', 'Colonia Los Laureles, Tegucigalpa'),
('Juan Carlos García', '08011985011223', 'Consumidor Final', '9999-8888', 'jgarcia@email.com', 'Residencial Las Haciendas'),
('María Fernanda López', '08011985033445', 'Consumidor Final', '8888-7777', 'mflopez@email.com', 'Colonia Miraflores, Tegucigalpa'),
('Distribuidora XYZ', '08011999000999', 'Empresa', '2255-4444', 'compras@xyz.hn', 'Parque Industrial, San Pedro Sula'),
('Pedro Antonio Martínez', '', 'Consumidor Final', '7777-6666', 'pmartinez@email.com', 'Barrio El Centro, Comayagua');

-- Insertar colecciones de ejemplo
INSERT INTO colecciones (nombre_coleccion, disenador_coleccion, num_coleccion_coleccion, anio_coleccion, descripcion_coleccion) VALUES
('Colección Dorada 2024', 'Roberto Silva', 'COL-2024-001', 2024, 'Colección exclusiva de espejos con marcos dorados de 24k'),
('Colección Artística', 'Luis Gómez', 'ART-2024-002', 2024, 'Muebles de madera maciza con diseños tallados a mano'),
('Colección Persica', 'Carmen Rodríguez', 'PER-2024-003', 2024, 'Alfombras tradicionales persicas 100% lana'),
('Colección Plateada', 'Ana Martínez', 'ESP-2024-001', 2024, 'Espejos modernos con marcos plateados');

-- Insertar decoraciones de ejemplo
INSERT INTO decoraciones (nombre_decoracion, stock_decoracion, stock_minimo_decoracion, stock_maximo_decoracion, id_proveedor_decoracion, id_coleccion_decoracion, descripcion_decoracion) VALUES
('Espejo Circular Dorado', 15, 5, 50, (SELECT id_proveedor FROM proveedores WHERE nombre_proveedor = 'Decoraciones S.A.'), (SELECT id_coleccion FROM colecciones WHERE num_coleccion_coleccion = 'COL-2024-001'), 'Espejo circular con marco dorado de 24k'),
('Lámpara de Cristal Moderna', 8, 3, 30, (SELECT id_proveedor FROM proveedores WHERE nombre_proveedor = 'Espejos y Lámparas HN'), NULL, 'Lámpara de techo con cristales colgantes'),
('Mesa de Centro Madera', 12, 4, 40, (SELECT id_proveedor FROM proveedores WHERE nombre_proveedor = 'Muebles del Arte'), (SELECT id_coleccion FROM colecciones WHERE num_coleccion_coleccion = 'ART-2024-002'), 'Mesa de centro de madera maciza con diseños tallados'),
('Cuadro Abstracto Grande', 20, 6, 60, (SELECT id_proveedor FROM proveedores WHERE nombre_proveedor = 'Decoraciones S.A.'), NULL, 'Cuadro abstracto con colores vibrantes'),
('Alfombra Persica', 6, 2, 25, (SELECT id_proveedor FROM proveedores WHERE nombre_proveedor = 'Alfombras Elegantes'), (SELECT id_coleccion FROM colecciones WHERE num_coleccion_coleccion = 'PER-2024-003'), 'Alfombra persica tradicional 100% lana'),
('Sofá de Cuero Negro', 4, 2, 15, (SELECT id_proveedor FROM proveedores WHERE nombre_proveedor = 'Muebles del Arte'), NULL, 'Sofá de tres plazas en cuero genuino'),
('Jarrón Cerámica Azul', 25, 8, 80, (SELECT id_proveedor FROM proveedores WHERE nombre_proveedor = 'Decoraciones S.A.'), NULL, 'Jarrón de cerámica artesanal con diseños azules'),
('Espejo Rectangular Plateado', 10, 3, 35, (SELECT id_proveedor FROM proveedores WHERE nombre_proveedor = 'Espejos y Lámparas HN'), (SELECT id_coleccion FROM colecciones WHERE num_coleccion_coleccion = 'ESP-2024-001'), 'Espejo rectangular con marco plateado');

-- Insertar facturas de compra de ejemplo
INSERT INTO facturas_compra (id_proveedor_factura_compra, numero_factura, total_factura_compra, tipo_pago_factura_compra, estado_factura_compra, fecha_factura_compra, fecha_vencimiento_factura, condicion_factura) VALUES
((SELECT id_proveedor FROM proveedores WHERE nombre_proveedor = 'Decoraciones S.A.'), 'FC-2024-001', 15000.00, 'Crédito', 'Pendiente', '2024-01-15', '2024-02-15', 'Crédito 30 días'),
((SELECT id_proveedor FROM proveedores WHERE nombre_proveedor = 'Espejos y Lámparas HN'), 'FC-2024-002', 8500.00, 'Contado', 'Pagada', '2024-01-20', NULL, 'Pago inmediato'),
((SELECT id_proveedor FROM proveedores WHERE nombre_proveedor = 'Muebles del Arte'), 'FC-2024-003', 12000.00, 'Crédito', 'Pagada', '2024-02-01', '2024-03-01', 'Crédito 30 días'),
((SELECT id_proveedor FROM proveedores WHERE nombre_proveedor = 'Alfombras Elegantes'), 'FC-2024-004', 20000.00, 'Crédito', 'Pendiente', '2024-02-10', '2024-03-10', 'Crédito 30 días');

-- Insertar detalle de compras de ejemplo
INSERT INTO detalle_compra (id_factura_compra_detalle, id_decoracion_detalle, cantidad_detalle_compra, precio_costo_compra, precio_venta_compra, subtotal_detalle_compra) VALUES
((SELECT id_factura_compra FROM facturas_compra WHERE numero_factura = 'FC-2024-001'), (SELECT id_decoracion FROM decoraciones WHERE nombre_decoracion = 'Espejo Circular Dorado'), 10, 1200.00, 1776.00, 12000.00),
((SELECT id_factura_compra FROM facturas_compra WHERE numero_factura = 'FC-2024-001'), (SELECT id_decoracion FROM decoraciones WHERE nombre_decoracion = 'Cuadro Abstracto Grande'), 3, 800.00, 1184.00, 2400.00),
((SELECT id_factura_compra FROM facturas_compra WHERE numero_factura = 'FC-2024-001'), (SELECT id_decoracion FROM decoraciones WHERE nombre_decoracion = 'Jarrón Cerámica Azul'), 2, 450.00, 666.00, 900.00),
((SELECT id_factura_compra FROM facturas_compra WHERE numero_factura = 'FC-2024-002'), (SELECT id_decoracion FROM decoraciones WHERE nombre_decoracion = 'Lámpara de Cristal Moderna'), 3, 2500.00, 3700.00, 7500.00),
((SELECT id_factura_compra FROM facturas_compra WHERE numero_factura = 'FC-2024-002'), (SELECT id_decoracion FROM decoraciones WHERE nombre_decoracion = 'Espejo Rectangular Plateado'), 2, 950.00, 1406.00, 1900.00),
((SELECT id_factura_compra FROM facturas_compra WHERE numero_factura = 'FC-2024-003'), (SELECT id_decoracion FROM decoraciones WHERE nombre_decoracion = 'Mesa de Centro Madera'), 5, 1800.00, 2664.00, 9000.00),
((SELECT id_factura_compra FROM facturas_compra WHERE numero_factura = 'FC-2024-003'), (SELECT id_decoracion FROM decoraciones WHERE nombre_decoracion = 'Sofá de Cuero Negro'), 1, 4500.00, 6660.00, 4500.00),
((SELECT id_factura_compra FROM facturas_compra WHERE numero_factura = 'FC-2024-004'), (SELECT id_decoracion FROM decoraciones WHERE nombre_decoracion = 'Alfombra Persica'), 4, 3500.00, 5180.00, 14000.00),
((SELECT id_factura_compra FROM facturas_compra WHERE numero_factura = 'FC-2024-004'), (SELECT id_decoracion FROM decoraciones WHERE nombre_decoracion = 'Espejo Circular Dorado'), 5, 1200.00, 1776.00, 6000.00);

-- Insertar ventas de ejemplo
INSERT INTO ventas (id_cliente_venta, numero_factura_venta, subtotal_venta, impuesto_venta, descuento_venta, total_venta, tipo_pago_venta, monto_efectivo, monto_tarjeta, cambio_venta, id_usuario_vendedor, fecha_venta) VALUES
((SELECT id_cliente FROM clientes WHERE nombre_cliente = 'Empresa ABC S.A.'), 'FV-2024-001', 3552.00, 639.36, 355.20, 3836.16, 'Efectivo', 4000.00, 0.00, 163.84, (SELECT id_usuario FROM usuarios WHERE user_usuario = 'jventas'), '2024-02-15 10:30:00'),
((SELECT id_cliente FROM clientes WHERE nombre_cliente = 'Juan Carlos García'), 'FV-2024-002', 1184.00, 213.12, 0.00, 1397.12, 'Tarjeta', 0.00, 1397.12, 0.00, (SELECT id_usuario FROM usuarios WHERE user_usuario = 'jventas'), '2024-02-16 14:20:00'),
((SELECT id_cliente FROM clientes WHERE nombre_cliente = 'María Fernanda López'), 'FV-2024-003', 5180.00, 932.40, 518.00, 5594.40, 'Mixto', 3000.00, 2594.40, 0.00, (SELECT id_usuario FROM usuarios WHERE user_usuario = 'cventas2'), '2024-02-17 09:15:00'),
((SELECT id_cliente FROM clientes WHERE nombre_cliente = 'Pedro Antonio Martínez'), 'FV-2024-004', 2664.00, 479.52, 0.00, 3143.52, 'Efectivo', 3200.00, 0.00, 56.48, (SELECT id_usuario FROM usuarios WHERE user_usuario = 'cventas2'), '2024-02-18 16:45:00'),
((SELECT id_cliente FROM clientes WHERE nombre_cliente = 'Empresa ABC S.A.'), 'FV-2024-005', 666.00, 119.88, 0.00, 785.88, 'Efectivo', 800.00, 0.00, 14.12, (SELECT id_usuario FROM usuarios WHERE user_usuario = 'jventas'), '2024-02-19 11:30:00');

-- Insertar detalle de ventas de ejemplo
INSERT INTO detalle_venta (id_venta_detalle, id_decoracion_detalle, cantidad_detalle_venta, precio_unitario_venta, subtotal_detalle_venta, descuento_detalle) VALUES
((SELECT id_venta FROM ventas WHERE numero_factura_venta = 'FV-2024-001'), (SELECT id_decoracion FROM decoraciones WHERE nombre_decoracion = 'Espejo Circular Dorado'), 2, 1776.00, 3552.00, 10.00),
((SELECT id_venta FROM ventas WHERE numero_factura_venta = 'FV-2024-002'), (SELECT id_decoracion FROM decoraciones WHERE nombre_decoracion = 'Cuadro Abstracto Grande'), 1, 1184.00, 1184.00, 0.00),
((SELECT id_venta FROM ventas WHERE numero_factura_venta = 'FV-2024-003'), (SELECT id_decoracion FROM decoraciones WHERE nombre_decoracion = 'Alfombra Persica'), 1, 5180.00, 5180.00, 10.00),
((SELECT id_venta FROM ventas WHERE numero_factura_venta = 'FV-2024-004'), (SELECT id_decoracion FROM decoraciones WHERE nombre_decoracion = 'Mesa de Centro Madera'), 1, 2664.00, 2664.00, 0.00),
((SELECT id_venta FROM ventas WHERE numero_factura_venta = 'FV-2024-005'), (SELECT id_decoracion FROM decoraciones WHERE nombre_decoracion = 'Jarrón Cerámica Azul'), 1, 666.00, 666.00, 0.00);

-- Insertar recibos de pago de ejemplo
INSERT INTO recibos_pago (id_factura_compra_pago, id_proveedor_pago, monto_pago, fecha_pago, metodo_pago, referencia_pago, observaciones_pago) VALUES
((SELECT id_factura_compra FROM facturas_compra WHERE numero_factura = 'FC-2024-002'), (SELECT id_proveedor FROM proveedores WHERE nombre_proveedor = 'Espejos y Lámparas HN'), 8500.00, '2024-01-20', 'Transferencia', 'TRN-2024-001', 'Pago completo al contado'),
((SELECT id_factura_compra FROM facturas_compra WHERE numero_factura = 'FC-2024-003'), (SELECT id_proveedor FROM proveedores WHERE nombre_proveedor = 'Muebles del Arte'), 6000.00, '2024-02-15', 'Transferencia', 'TRN-2024-002', 'Pago parcial'),
((SELECT id_factura_compra FROM facturas_compra WHERE numero_factura = 'FC-2024-003'), (SELECT id_proveedor FROM proveedores WHERE nombre_proveedor = 'Muebles del Arte'), 6000.00, '2024-02-28', 'Cheque', 'CHQ-2024-001', 'Liquidación factura');

-- Desactivar modo seguro para permitir actualizaciones sin clave primaria
SET SQL_SAFE_UPDATES = 0;

-- Actualizar stock de decoraciones según las ventas
UPDATE decoraciones SET stock_decoracion = stock_decoracion - 2 WHERE nombre_decoracion = 'Espejo Circular Dorado';
UPDATE decoraciones SET stock_decoracion = stock_decoracion - 1 WHERE nombre_decoracion = 'Cuadro Abstracto Grande';
UPDATE decoraciones SET stock_decoracion = stock_decoracion - 1 WHERE nombre_decoracion = 'Alfombra Persica';
UPDATE decoraciones SET stock_decoracion = stock_decoracion - 1 WHERE nombre_decoracion = 'Mesa de Centro Madera';
UPDATE decoraciones SET stock_decoracion = stock_decoracion - 1 WHERE nombre_decoracion = 'Jarrón Cerámica Azul';

-- Actualizar estado de facturas de compra según pagos
UPDATE facturas_compra SET estado_factura_compra = 'Pagada' WHERE numero_factura = 'FC-2024-002';
UPDATE facturas_compra SET estado_factura_compra = 'Pagada' WHERE numero_factura = 'FC-2024-003';

-- Reactivar modo seguro
SET SQL_SAFE_UPDATES = 1;