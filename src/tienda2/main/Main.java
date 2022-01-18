package main;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import bbdd.AccesoBBDD;
import objetos.Producto;

public class Main {

	private static String path1 = "productos.dat";
	private static File f1 = new File(path1);

	private static String path2 = "vendidos.txt";
	private static File f2 = new File(path2);

	public static void main(String[] args) {
		//f2.delete();
		Scanner sc = new Scanner(System.in);
		ArrayList<Producto> array = new ArrayList<Producto>();

		System.out.println(AccesoBBDD.escribirFichero(f1));
		array = AccesoBBDD.leerFichero(f1);
		AccesoBBDD.ventaProductos(array, f2, sc);

	}

}
