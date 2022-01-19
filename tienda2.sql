
--
-- Base de datos: `tienda`
--

CREATE DATABASE IF NOT EXISTS tienda2;

USE tienda2;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articulos`
--

CREATE TABLE IF NOT EXISTS `articulos` (
  `ClArt` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `Nombre` varchar(30) NOT NULL,
  `Precio` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL
) ;

INSERT INTO `articulos`(`ClArt`, `Nombre`, `Precio`, `cantidad`) VALUES (1,"Teclado TS11",30,100);
INSERT INTO `articulos`(`ClArt`, `Nombre`, `Precio`, `cantidad`) VALUES (2,"Raton JGD",50,20);
INSERT INTO `articulos`(`ClArt`, `Nombre`, `Precio`, `cantidad`) VALUES (3,"Ordenador orp",500,5);
INSERT INTO `articulos`(`ClArt`, `Nombre`, `Precio`, `cantidad`) VALUES (4,"Cascos g",40,200);
INSERT INTO `articulos`(`ClArt`, `Nombre`, `Precio`, `cantidad`) VALUES (5,"Monitor RRAS",200,20);
