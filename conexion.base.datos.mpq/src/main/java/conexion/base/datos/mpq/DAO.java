package conexion.base.datos.mpq;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO {

	private static Connection conexion;

	private static Statement conectar() {

		try {
			BufferedReader lector = new BufferedReader(new FileReader("mysql.cnf"));
			String ip = lector.readLine();
			int puerto = Integer.parseInt(lector.readLine());
			String nombreBD = lector.readLine();
			String user = lector.readLine();
			String password = lector.readLine();
			lector.close();
			conexion = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + puerto + "/" + nombreBD, user,
					password);
			return conexion.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private static void desconectar(Statement s) {
		try {
			s.close();
			conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
