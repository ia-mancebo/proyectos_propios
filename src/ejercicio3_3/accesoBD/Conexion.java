package accesoBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	 /*
	    * conexion
	    * */
	   public static Connection conexion(String url, String usuario, String contrasenia) {
		 //No se puede usar el try con recursos, porque se cerraría la bd
		     try { 		    	 
		    	   Connection conexion=DriverManager.getConnection (url, usuario, contrasenia);	
		    	   System.out.println("Nos conectamos");
		    	   return conexion;
		    	 } catch(SQLException e) {
		    	    System.out.println(e);
		    	 } catch(Exception e) {
		    	   System.out.println(e);
		    	 }
		     
		     return null;
	   }
	  
	   /*
	    * desConexion
	    * */
	   public static void desConexion(Connection conexion){		   
		     try { 		    	 
		    	 if (conexion !=null) {
		    		 System.out.println("Nos desconectamos");		    	   
		    		 conexion.close();
		    	 }
		     } catch(SQLException e) {
		    	    System.out.println(e);
		     } catch(Exception e) {
		    	   System.out.println(e);
		     }
	   }
	   
}
