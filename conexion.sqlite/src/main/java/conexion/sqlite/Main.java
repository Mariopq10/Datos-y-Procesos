package conexion.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		//Primeramente importamos el .jar de sqlite3
		
		// Hacemos la conexion a la base de datos de sqlite que hemos creado e
		// introducido en la carpeta del proyecto, no necesitamos usuario y contraseña
		// como en el ejercicio anterior.
		try (Connection con = DriverManager.getConnection("jdbc:sqlite:./database.db")) {
			// Realizamos la conexion con la base de datos
			Statement stmt = con.createStatement();

			// Ejecutamos la linea de mysql que crea la tabla incidencias, crea la tabla
			// solo si no esta creada.
			stmt.execute(
					"CREATE TABLE IF NOT EXISTS clientes (id int auto_increment primary key, nombre text,apellido text);");
			
			// PreparedStatement la lineaInsert introduciendo datos manualmente.

			String lineaInsert = "INSERT INTO clientes (id,nombre,apellido) VALUES (?,?,?)";
			try (PreparedStatement state = con.prepareStatement(lineaInsert)) {
				state.setInt(1, 1);
				state.setString(2, "Mario");
				state.setString(3,"Perez");
				state.executeUpdate();
				state.close();
			}

			// Sentencia SELECT para coger todos los datos de incidencias y mostrarlas por
			// consola.

			ResultSet rs2 = stmt.executeQuery("select * from clientes");
			while (rs2.next())
				System.out.println(rs2.getInt(1) + " - " + rs2.getString(2) + " - " + rs2.getString(3) + " - ");
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
