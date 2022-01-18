package bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import excepciones.CodigoNoEncontrado;
import excepciones.NoHayUnidadesException;
import objetos.Producto;

import java.io.*;

public class AccesoBBDD {

	public static boolean escribirFichero(File f1) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f1));
				Connection conn = Conexion.conexion(ConstantesBBDD.URL + "tienda2", ConstantesBBDD.USER,
						ConstantesBBDD.PWD)) {
			PreparedStatement ps = conn.prepareStatement("select * from articulos;");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				oos.writeObject(new Producto(rs.getInt("CLART"), rs.getInt("precio"), rs.getInt("cantidad"),
						rs.getString("nombre")));
			}
			return true;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}

	public static ArrayList<Producto> leerFichero(File f1) {
		ArrayList<Producto> devolver = new ArrayList<Producto>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f1))) {
			while (true) {
				devolver.add((Producto) ois.readObject());
			}
		} catch (EOFException e) {
			return devolver;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void ventaProductos(ArrayList<Producto> array, File f1, Scanner sc) {
		int codigo, cantidad, indice, repetir = 1, total = 0;
		while (repetir == 1) {
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(f1, true))) {
				System.out.println(array);
				System.out.print("Elija el codigo del producto a comprar: ");
				codigo = Integer.parseInt(sc.nextLine());
				System.out.println("Cantidad: ");
				cantidad = Integer.parseInt(sc.nextLine());

				indice = devolverPosInt(array, codigo);

				array.get(indice).disminuirCantidad(cantidad);
				actualizarBD(array.get(indice));

				total += array.get(indice).getPrecio() * cantidad;

				bw.write(array.get(indice).getNombre() + " " + cantidad + " x " + array.get(indice).getPrecio() + " = "
						+ (array.get(indice).getPrecio() * cantidad));
				bw.newLine();
				System.out.println("Quieres comprar algo mas(1.y/2.n): ");
				repetir = Integer.parseInt(sc.nextLine());

				if (repetir != 1) {
					bw.newLine();
					bw.write("TOTAL: " + total);
					bw.newLine();
					bw.write("----------------------------");
					bw.newLine();
				}

			} catch (NumberFormatException e) {
				System.out.println("El valor introducido no es un numero");
			} catch (CodigoNoEncontrado e) {
				System.out.println(e);
			} catch (NoHayUnidadesException e) {
				System.out.println(e);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}

	public static int devolverPosInt(ArrayList<Producto> array, int codigo) throws CodigoNoEncontrado {
		for (int i = 0; i < array.size(); i++) {
			if (codigo == array.get(i).getCodigo()) {
				return i;
			}
		}
		throw new CodigoNoEncontrado("No existe el producto indicado");
	}

	public static void actualizarBD(Producto p) {
		try (Connection conn = Conexion.conexion(ConstantesBBDD.URL + "tienda2", ConstantesBBDD.USER,
				ConstantesBBDD.PWD)) {
			PreparedStatement ps = conn.prepareStatement("update articulos set cantidad=? where clart=?");
			ps.setInt(1, p.getCantidad());
			ps.setInt(2, p.getCodigo());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}