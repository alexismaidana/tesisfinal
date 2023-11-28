/* Script para la generación de valores iniciales */
/* Roles */
INSERT INTO `tesisfinalAM_db`.`permisos` (`id`, `nombre`) VALUES ('1', 'ROLE_USER');
INSERT INTO `tesisfinalAM_db`.`permisos` (`id`, `nombre`) VALUES ('2', 'ROLE_ADMIN');
INSERT INTO `tesisfinalAM_db`.`permisos` (`id`, `nombre`) VALUES ('3', 'ROLE_EMPLEADO');
INSERT INTO `tesisfinalAM_db`.`permisos` (`id`, `nombre`) VALUES ('4', 'ROLE_CLIENTE');

INSERT INTO `tesisfinalAM_db`.`sucursales` (`id`, `nombre`) VALUES ('1', 'Sucursal Fontana');
INSERT INTO `tesisfinalAM_db`.`sucursales` (`id`, `nombre`) VALUES ('2', 'Sucursal Resistencia');

/* Usuarios - en las pruebas todas las claves son 'usuario' */
INSERT INTO `tesisfinalAM_db`.`usuarios` (`activo`, `clave`,`email`,`nombre`,`id_permiso`, `id_sucursal`) VALUES ('1', '$2a$10$nmsnELze.Ca7dMnsbfGIuuczJlKMAk9SGCkgDczmosj91zCAMsFoO', 'cmarangoni8@gmail.com', 'claudio', '2', '1');
INSERT INTO `tesisfinalAM_db`.`usuarios` (`activo`, `clave`, `email`, `nombre`, `id_permiso`, `id_sucursal`) VALUES ('1', '$2a$10$nmsnELze.Ca7dMnsbfGIuuczJlKMAk9SGCkgDczmosj91zCAMsFoO', 'alexis@gmail.com', 'alexis', '1', '1');
INSERT INTO `tesisfinalAM_db`.`usuarios` (`activo`, `clave`, `email`, `nombre`, `id_permiso`, `id_sucursal`) VALUES ('1', '$2a$10$nmsnELze.Ca7dMnsbfGIuuczJlKMAk9SGCkgDczmosj91zCAMsFoO', 'alexis@gmail.com', 'alexisc', '4', '1');

/*Cargar Categoria*/
INSERT INTO `tesisfinalAM_db`.`categorias` (`nombre`) VALUES ('Almacenamiento'); 
INSERT INTO `tesisfinalAM_db`.`categorias` (`nombre`) VALUES ('Procesadores');
INSERT INTO `tesisfinalAM_db`.`categorias` (`nombre`) VALUES ('Mother board');
INSERT INTO `tesisfinalAM_db`.`categorias` (`nombre`) VALUES ('Memorias');

/*Cargar Proveedores*/
INSERT INTO `tesisfinalAM_db`.`proveedores` (`activo`, `cuil`, `direccion`, `email`, `localidad`, `razon_soc`, `telefono`) VALUES (1, '20318720852', 'Jose Hernandez 321', 'megatone@gamil.com', 'Fontana', 'Megatone', '3624883252');
INSERT INTO `tesisfinalAM_db`.`proveedores` (`activo`, `cuil`, `direccion`, `email`, `localidad`, `razon_soc`, `telefono`) VALUES (1, '27320889389', 'Avenida 9 de Julio 315', 'ryr@gamil.com', 'Resistencia', 'RyR Computación', '3624883252');

/*Cargar Productos*/
INSERT INTO `tesisfinalAM_db`.`productos` (`id`, `activo`, `cod_bar`, `descripcion`, `lnk_img`, `precio`, `precio_compra`, `stock_general`, `stock_sucursal_dos`, `stock_sucursal_uno`, `titulo`, `id_categoria`, `id_proveedor`) VALUES ('1', 1, 'NEXUS001', 'SOCKET 1700 INTEL CORE I5 12400 4.4GHZ 6N 12H', 'https://app.contabilium.com/files/explorer/7026/Productos-Servicios/concepto-7211270.jpg', '2000', '1000', '50', '25', '25', 'PROCESADOR', '1', '1');
INSERT INTO `tesisfinalAM_db`.`productos` (`id`, `activo`, `cod_bar`, `descripcion`, `lnk_img`, `precio`, `precio_compra`, `stock_general`, `stock_sucursal_dos`, `stock_sucursal_uno`, `titulo`, `id_categoria`, `id_proveedor`) VALUES ('2', 1, 'NEXUS002', 'SSD SATA 480GB KINGSTON A400', 'https://th.bing.com/th/id/R.432c8ecbbd5f7d330b0eda41e6e3fd29?rik=TCikpcjaRtoWRQ&pid=ImgRaw&r=0', '149799', '400','50', '25', '25', 'DISCO SOLIDO','2', '2');
INSERT INTO `tesisfinalAM_db`.`productos` (`id`, `activo`, `cod_bar`, `descripcion`, `lnk_img`, `precio`, `precio_compra`, `stock_general`, `stock_sucursal_dos`, `stock_sucursal_uno`, `titulo`, `id_categoria`, `id_proveedor`) VALUES ('3', 1, 'NEXUS003', 'PLACA MADRE ATX AM4 ASUS ROG STRIX B550-F GAMING', 'https://app.contabilium.com/files/explorer/16752/Productos-Servicios/concepto-3829845.jpg', '123999', '1000', '60', '30', '30', 'Motherboard', '1', '1');
INSERT INTO `tesisfinalAM_db`.`productos` (`id`, `activo`, `cod_bar`, `descripcion`, `lnk_img`, `precio`, `precio_compra`, `stock_general`, `stock_sucursal_dos`, `stock_sucursal_uno`, `titulo`, `id_categoria`, `id_proveedor`) VALUES ('4', 1, 'NEXUS004', 'MEMORIA RAM DDR4 8GB 3600MHZ KINGSTON FURY BEAST', 'https://media.kingston.com/kingston/features/ktc-features-memory-beast-ddr4.jpg', '49799', '400','44', '20', '22', 'Memorias Ram','2', '2');

/*Cargar Provincias*/
INSERT INTO `tesisfinalAM_db`.`provincias` (`id`, `nombre`) VALUES ('1', 'Buenos Aires');
INSERT INTO `tesisfinalAM_db`.`provincias` (`id`, `nombre`) VALUES ('2', 'Catamarfca');
INSERT INTO `tesisfinalAM_db`.`provincias` (`id`, `nombre`) VALUES ('3', 'Chaco');
INSERT INTO `tesisfinalAM_db`.`provincias` (`id`, `nombre`) VALUES ('4', 'Chubut');
INSERT INTO `tesisfinalAM_db`.`provincias` (`id`, `nombre`) VALUES ('5', 'Cordoba');
INSERT INTO `tesisfinalAM_db`.`provincias` (`id`, `nombre`) VALUES ('6', 'Corrientes');
INSERT INTO `tesisfinalAM_db`.`provincias` (`id`, `nombre`) VALUES ('7', 'Jujuy');
INSERT INTO `tesisfinalAM_db`.`provincias` (`id`, `nombre`) VALUES ('8', 'La Pampa');
INSERT INTO `tesisfinalAM_db`.`provincias` (`id`, `nombre`) VALUES ('9', 'La Rioja');
INSERT INTO `tesisfinalAM_db`.`provincias` (`id`, `nombre`) VALUES ('10', 'Mendoza');
INSERT INTO `tesisfinalAM_db`.`provincias` (`id`, `nombre`) VALUES ('11', 'Misiones');
INSERT INTO `tesisfinalAM_db`.`provincias` (`id`, `nombre`) VALUES ('12', 'Neuquen');
INSERT INTO `tesisfinalAM_db`.`provincias` (`id`, `nombre`) VALUES ('13', 'Rio Negro');
INSERT INTO `tesisfinalAM_db`.`provincias` (`id`, `nombre`) VALUES ('14', 'Salta');
INSERT INTO `tesisfinalAM_db`.`provincias` (`id`, `nombre`) VALUES ('15', 'San Juan');
INSERT INTO `tesisfinalAM_db`.`provincias` (`id`, `nombre`) VALUES ('16', 'Santa Cruz');
INSERT INTO `tesisfinalAM_db`.`provincias` (`id`, `nombre`) VALUES ('17', 'Santa Fe');
INSERT INTO `tesisfinalAM_db`.`provincias` (`id`, `nombre`) VALUES ('18', 'Santiago del Estero');
INSERT INTO `tesisfinalAM_db`.`provincias` (`id`, `nombre`) VALUES ('19', 'Tierra del Fuego');
INSERT INTO `tesisfinalAM_db`.`provincias` (`id`, `nombre`) VALUES ('20', 'Tucuman');

