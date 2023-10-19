package jdbc.mpq;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Main {

	public static void main(String[] args) {
		//Preinstalamos los drivers de conexion de la base de datos desde la página de mysql e importamos el archivo.jar como libreria externa.
		try (Connection con = DriverManager.getConnection("jdbc:mysql://192.168.6.221:3306/mario", "mario", "12345")) {
			//Realizamos la conexion con la base de datos
			Statement stmt = con.createStatement();
			//ResultSet almacena la busqueda del query ejecutado del string "select * from clientes"
			ResultSet busqueda = stmt.executeQuery("select * from clientes");
			while (busqueda.next())
				System.out.println(busqueda.getInt(1) + " " + busqueda.getString(2));
			//Cerramos la conexion
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		/*
		 * Properties configuracion = new Properties(); try {
		 * 
		 * configuracion.load(new FileInputStream("mysql.cnf"));
		 * 
		 * String usuario = configuracion.getProperty("user");
		 * 
		 * String password = configuracion.getProperty("password");
		 * 
		 * String servidor = configuracion.getProperty("server");
		 * 
		 * int puerto = Integer.valueOf(configuracion.getProperty("port"));
		 * 
		 * System.out.println("Usuario: " + usuario + "\nContrasena: " + password +
		 * "\nServidor: " + servidor + "\nPuerto: " + puerto);
		 * 
		 * } catch (FileNotFoundException fnfe) {
		 * 
		 * fnfe.printStackTrace();
		 * 
		 * } catch (IOException ioe) {
		 * 
		 * ioe.printStackTrace();
		 * 
		 * }
		 */

	}

}
