package accesoBD;

import java.io.*;
import java.sql.*;

import objetos.Produccion;

public class AccesoDB {

	public static void crearBD(String nombre, Connection con) {
		if (con == null) {
			con = Conexion.conexion(ConstantesBD.URL, ConstantesBD.USUARIO, ConstantesBD.PWD);
		}

		try {
			PreparedStatement ps = con.prepareStatement("create database if not exists " + ConstantesBD.BD);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void crearProduccion(String nomTabla, Connection con) {
		if (con == null) {
			con = Conexion.conexion(ConstantesBD.URL, ConstantesBD.USUARIO, ConstantesBD.PWD);
		}

		try {
			PreparedStatement ps = con
					.prepareStatement("create table " + nomTabla + " (" + "id int AUTO_INCREMENT primary key,"
							+ "marca varchar(50)," + "fabrica varchar(50)," + "unidades int);");
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertarProduccion(Produccion produccion, Connection con) {
		if (con == null) {
			con = Conexion.conexion(ConstantesBD.URL, ConstantesBD.USUARIO, ConstantesBD.PWD);
		}

		try {
			PreparedStatement ps = con.prepareStatement(
					"insert into " + ConstantesBD.NOMBRE_TABLA + " (marca,fabrica,unidades) values (?,?,?)");
			ps.setString(1, produccion.getMarca());
			ps.setString(2, produccion.getFabrica());
			ps.setInt(3, produccion.getUnidades());

			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void borrarProduccion(int id, Connection con) {
		if (con == null) {
			con = Conexion.conexion(ConstantesBD.URL, ConstantesBD.USUARIO, ConstantesBD.PWD);
		}
		try {
			PreparedStatement ps = con.prepareStatement("delete from " + ConstantesBD.NOMBRE_TABLA + " where id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void crearFichero(String nomFich, Connection con) {
		if (con == null) {
			con = Conexion.conexion(ConstantesBD.URL, ConstantesBD.USUARIO, ConstantesBD.PWD);
		}

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomFich))) {
			PreparedStatement ps = con.prepareStatement("select * from " + ConstantesBD.NOMBRE_TABLA);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String marca = rs.getString(2);
				String fabrica = rs.getString(3);
				int unidades = rs.getInt(4);

				oos.writeObject(new Produccion(id, marca, fabrica, unidades));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void mostrarFichero(String nomFich) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomFich))) {
			while (true) {
				Produccion p;
				p = (Produccion) ois.readObject();
				System.out.println(p);
			}
		} catch (EOFException e) {

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void seleccionarTabla(String nomTabla, Connection con) {
		try {
			Statement st = con.createStatement();
			st.execute("use " + nomTabla);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
