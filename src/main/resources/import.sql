/* Script para la generación de valores iniciales */
/* Roles */
INSERT INTO `nexus_db`.`permisos` (`id`, `nombre`) VALUES ('1', 'ROLE_USER');
INSERT INTO `nexus_db`.`permisos` (`id`, `nombre`) VALUES ('2', 'ROLE_ADMIN');
INSERT INTO `nexus_db`.`permisos` (`id`, `nombre`) VALUES ('3', 'ROLE_CLIENTE');

/* Usuarios - en las pruebas todas las claves son 'usuario' */
INSERT INTO `nexus_db`.`usuarios` (`activo`, `clave`,`email`,`nombre`,`id_permiso`) VALUES ('1', '$2a$10$nmsnELze.Ca7dMnsbfGIuuczJlKMAk9SGCkgDczmosj91zCAMsFoO', 'cmarangoni8@gmail.com', 'claudio', '2');
INSERT INTO `nexus_db`.`usuarios` (`activo`, `clave`, `email`, `nombre`, `id_permiso`) VALUES ('1', '$2a$10$nmsnELze.Ca7dMnsbfGIuuczJlKMAk9SGCkgDczmosj91zCAMsFoO', 'cmarangoni8@gmail.com', 'alan', '1');

/*Cargar Categoria*/
INSERT INTO `nexus_db`.`categorias` (`nombre`) VALUES ('Almacenamiento'); 
INSERT INTO `nexus_db`.`categorias` (`nombre`) VALUES ('Procesadores');
INSERT INTO `nexus_db`.`categorias` (`nombre`) VALUES ('Mother board');
INSERT INTO `nexus_db`.`categorias` (`nombre`) VALUES ('Memorias');

/*Cargar Productos*/
INSERT INTO `nexus_db`.`productos` (`cod_bar`, `activo`, `descripcion`, `lnk_img`, `precio`, `stock`, `id_categoria`) VALUES ('220883877824', 1, 'PROCESADOR SOCKET 1700 INTEL CORE I5 12400 4.4GHZ 6N 12H', 'https://app.contabilium.com/files/explorer/7026/Productos-Servicios/concepto-7211270.jpg', '149799','20', 2);
INSERT INTO `nexus_db`.`productos` (`cod_bar`, `activo`, `descripcion`, `lnk_img`, `precio`, `stock`, `id_categoria`) VALUES ('566791639351', 1, 'DISCO SOLIDO SSD SATA 480GB KINGSTON A400', 'https://th.bing.com/th/id/R.432c8ecbbd5f7d330b0eda41e6e3fd29?rik=TCikpcjaRtoWRQ&pid=ImgRaw&r=0', '24999', '50', 1);
INSERT INTO `nexus_db`.`productos` (`cod_bar`, `activo`, `descripcion`, `lnk_img`, `precio`, `stock`, `id_categoria`) VALUES ('941666160941', 1, 'MEMORIA RAM DDR4 16GB 3200MHZ CORSAIR VENGEANCE RGB RS', 'https://th.bing.com/th/id/OIP.y_7SKEvKSWT8pvpTdSj5iQHaHa?pid=ImgDet&rs=1', '41999', '34', 4);
INSERT INTO `nexus_db`.`productos` (`cod_bar`, `activo`, `descripcion`, `lnk_img`, `precio`, `stock`, `id_categoria`) VALUES ('550247054600', 1,'DISCO SOLIDO SSD M.2 NVME 1TB SAMSUNG EVO 970', 'https://images.samsung.com/is/image/samsung/es-ssd-970-evo-mz-v7e1t0bw-frontblack-96266700?$1300_1038_PNG$', '65999', '10', 1);
INSERT INTO `nexus_db`.`productos` (`cod_bar`, `activo`, `descripcion`, `lnk_img`, `precio`, `stock`, `id_categoria`) VALUES ('923809328947', 1, 'PROCESADOR SOCKET AM4 AMD RYZEN 7 5800X 4.7GHZ 8N 16H', 'https://app.contabilium.com/files/explorer/16752/Productos-Servicios/concepto-5305270.jpg', '214999', '10', 2);
INSERT INTO `nexus_db`.`productos` (`cod_bar`, `activo`, `descripcion`, `lnk_img`, `precio`, `stock`, `id_categoria`) VALUES ('119961677773', 1, 'PLACA MADRE ATX AM4 ASUS ROG STRIX B550-F GAMING', 'https://app.contabilium.com/files/explorer/16752/Productos-Servicios/concepto-3829845.jpg', '143999', '5', 3);
INSERT INTO `nexus_db`.`productos` (`cod_bar`, `activo`, `descripcion`, `lnk_img`, `precio`, `stock`, `id_categoria`) VALUES ('751343833889', 1,'DISCO SOLIDO SSD 240GB WESTERN DIGITAL GREEN', 'https://spacegamer.com.ar/img/Public/1058-producto-disco-estado-solido-ssd-240gb-25-sata-3-wd-green-519.jpg', '13999', '20', 1);
INSERT INTO `nexus_db`.`productos` (`cod_bar`, `activo`, `descripcion`, `lnk_img`, `precio`, `stock`, `id_categoria`) VALUES ('658114399201', 1, 'PLACA MADRE ATX S1700 GIGABYTE Z690 UD DDR4', 'https://front.dev.malditohard.com.ar/img/migration/MOTHER-INTEL-GIGABYTE-Z690-UD-DDR4-G10-1700.webp', '175999', '5', 3);
INSERT INTO `nexus_db`.`productos` (`cod_bar`, `activo`, `descripcion`, `lnk_img`, `precio`, `stock`, `id_categoria`) VALUES ('337475106884', 1, 'MEMORIA RAM DDR4 8GB 3600MHZ KINGSTON FURY BEAST', 'https://media.kingston.com/kingston/features/ktc-features-memory-beast-ddr4.jpg', '40999', '14', 4);

/*Cargar Provincias*/
INSERT INTO `nexus_db`.`provincias` (`id`, `nombre`) VALUES ('1', 'Buenos Aires');
INSERT INTO `nexus_db`.`provincias` (`id`, `nombre`) VALUES ('2', 'Catamarfca');
INSERT INTO `nexus_db`.`provincias` (`id`, `nombre`) VALUES ('3', 'Chaco');
INSERT INTO `nexus_db`.`provincias` (`id`, `nombre`) VALUES ('4', 'Chubut');
INSERT INTO `nexus_db`.`provincias` (`id`, `nombre`) VALUES ('5', 'Cordoba');
INSERT INTO `nexus_db`.`provincias` (`id`, `nombre`) VALUES ('6', 'Corrientes');
INSERT INTO `nexus_db`.`provincias` (`id`, `nombre`) VALUES ('7', 'Jujuy');
INSERT INTO `nexus_db`.`provincias` (`id`, `nombre`) VALUES ('8', 'La Pampa');
INSERT INTO `nexus_db`.`provincias` (`id`, `nombre`) VALUES ('9', 'La Rioja');
INSERT INTO `nexus_db`.`provincias` (`id`, `nombre`) VALUES ('10', 'Mendoza');
INSERT INTO `nexus_db`.`provincias` (`id`, `nombre`) VALUES ('11', 'Misiones');
INSERT INTO `nexus_db`.`provincias` (`id`, `nombre`) VALUES ('12', 'Neuquen');
INSERT INTO `nexus_db`.`provincias` (`id`, `nombre`) VALUES ('13', 'Rio Negro');
INSERT INTO `nexus_db`.`provincias` (`id`, `nombre`) VALUES ('14', 'Salta');
INSERT INTO `nexus_db`.`provincias` (`id`, `nombre`) VALUES ('15', 'San Juan');
INSERT INTO `nexus_db`.`provincias` (`id`, `nombre`) VALUES ('16', 'Santa Cruz');
INSERT INTO `nexus_db`.`provincias` (`id`, `nombre`) VALUES ('17', 'Santa Fe');
INSERT INTO `nexus_db`.`provincias` (`id`, `nombre`) VALUES ('18', 'Santiago del Estero');
INSERT INTO `nexus_db`.`provincias` (`id`, `nombre`) VALUES ('19', 'Tierra del Fuego');
INSERT INTO `nexus_db`.`provincias` (`id`, `nombre`) VALUES ('20', 'Tucuman');

/*Cargar Proveedores*/
INSERT INTO `nexus_db`.`proveedores` (`activo`, `cuil`, `direccion`, `email`, `localidad`, `razon_soc`, `telefono`) VALUES (1, '20318720852', 'Jose Hernandez 321', 'megatone@gamil.com', 'Fontana', 'Megatone', '3624883252');
INSERT INTO `nexus_db`.`proveedores` (`activo`, `cuil`, `direccion`, `email`, `localidad`, `razon_soc`, `telefono`) VALUES (1, '27320889389', 'Avenida 9 de Julio 315', 'ryr@gamil.com', 'Resistencia', 'RyR Computación', '3624883252');