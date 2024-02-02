package psp.serv.reparaciones.cliente.mpq;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	private static final String JDBC_URL = "jdbc:postgresql://143.47.32.113:5432/tienda";
	private static final String USER = "mario";
	private static final String PASSWORD = "12345";

	public static void main(String[] args) {

		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}

		// Establecer la conexión con la base de datos.
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
			// Listar los partes asociados a un tecnico.

			// Listar toda la tabla
			listarTabla(connection, 2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void listarTabla(Connection connection, int codigoParte) throws SQLException {
		String sql = "SELECT * FROM partes WHERE codigo_tecnico=" + codigoParte + "";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				int parte = resultSet.getInt("id_parte");
				String descripcion = resultSet.getString("descripcion");
				String lugar = resultSet.getString("lugar");
				Date fecha = resultSet.getDate("fecha_parte");
				System.out.println("Parte: " + parte + ", Descripcion: " + descripcion + ", Lugar del parte: " + lugar);
			}
		}
	}
}
