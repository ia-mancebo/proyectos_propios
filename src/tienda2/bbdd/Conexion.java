package bbdd;

import java.sql.*;

public class Conexion {

	public static Connection conexion(String url, String user, String pwd) {
		try  {
			Connection conn = DriverManager.getConnection(url, user, pwd);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
			return null;
	}
	
	public static void deconexion(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
