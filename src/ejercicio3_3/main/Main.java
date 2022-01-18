package main;

import accesoBD.*;
import objetos.*;
import java.sql.*;
import java.util.Scanner;

public class Main {

	public static String nomFich = "fichero.obj";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Connection con = Conexion.conexion(ConstantesBD.URL, ConstantesBD.USUARIO, ConstantesBD.PWD);
		System.out.println("Vamos a crear la base de datos si no existe");
		AccesoDB.crearBD(ConstantesBD.BD, con);
		System.out.println("Seleccionamos la base de datos");
		AccesoDB.seleccionarTabla(ConstantesBD.BD, con);
		System.out.println("Vamos a crear la tabla produccion");
		AccesoDB.crearProduccion(ConstantesBD.NOMBRE_TABLA, con);
		System.out.println("Vamos a pedir datos para insertar en la tabla");
		insertarProduccionesBucle(sc, con);
		System.out.println("Borramos una fila");
		borrarFilaUsu(sc, con);
		System.out.println("Escribimos el fichro");
		AccesoDB.crearFichero(nomFich, con);
		System.out.println("Leemos el fichero");
		AccesoDB.mostrarFichero(nomFich);

		sc.close();
		Conexion.desConexion(con);

	}

	public static void insertarProduccionesBucle(Scanner sc, Connection con) {
		boolean repetir = true;
		String repetirString;
		while (repetir) {
			try {
				System.out.println("\nNombre de la marca: ");
				String marca = sc.nextLine();
				EnumMarcas marcaEnum = EnumMarcas.valueOf(marca.toUpperCase());
				System.out.println("Unidades producidas: ");
				int unidades = Integer.parseInt(sc.nextLine());

				AccesoDB.insertarProduccion(new Produccion(0, marcaEnum, marcaEnum.getLoc(), unidades), con);
			} catch (IllegalArgumentException e) {
				System.out.println("No es un tipo aceptado");
			}
			System.out.println("\nDesea seguir aniadiendo producciones (S/N)");
			repetirString = sc.nextLine();
			if (repetirString.equalsIgnoreCase("s"))
				repetir = true;
			else if (repetirString.equalsIgnoreCase("n"))
				repetir = false;
			else
				repetir = false;
		}
	}

	public static void borrarFilaUsu(Scanner sc, Connection con) {

		System.out.println("Introduce el identificador de la produccion a borrar: ");
		int id = Integer.parseInt(sc.nextLine());
		AccesoDB.borrarProduccion(id, con);
	}

}
