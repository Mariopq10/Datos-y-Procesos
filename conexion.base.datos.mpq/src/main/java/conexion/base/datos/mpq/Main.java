package conexion.base.datos.mpq;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// Preinstalamos los drivers de conexion de la base de datos desde la página de
		// mysql e importamos el archivo.jar como libreria externa.
		try (Connection con = DriverManager.getConnection("jdbc:mysql://192.168.6.221:3306/mario", "mario", "12345")) {
			// Realizamos la conexion con la base de datos
			Statement stmt = con.createStatement();
			// ResultSet almacena la busqueda del query ejecutado del string "select * from
			// clientes"
			// ResultSet busqueda = stmt.executeQuery("select * from clientes");
			// while (busqueda.next())
			// System.out.println(busqueda.getInt(1) + " " + busqueda.getString(2));

			stmt.execute(
					"CREATE TABLE IF NOT EXISTS incidencias (id int auto_increment primary key, codigo_cliente int, fecha datetime, descripcion varchar(50), n_horas int)");
			/*
			 * stmt.execute("ALTER TABLE incidencias\r\n" +
			 * "ADD FOREIGN KEY (codigo_cliente) REFERENCES clientes(id);");
			 */
			// Cerramos la conexion

			System.out.println("Introduce codigo cliente");
			String codigo = sc.nextLine();
			System.out.println("Descripcion de la incidencia");
			String descripcion = sc.nextLine();
			System.out.println("Introduce numero de horas");
			String horasIncidencia = sc.nextLine();

			LocalDateTime horaL = LocalDateTime.now();
			Timestamp fecha = Timestamp.valueOf(horaL);
			
			stmt.execute("insert into incidencias (id,codigo_cliente,fecha,descripcion,n_horas) values(" + codigo + ","
					+ fecha + ",'" + descripcion + "'," + Integer.parseInt(horasIncidencia));

			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}
}
