package ad.postgresql.mpq;

import java.sql.Connection;
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
		Scanner sc = new Scanner(System.in);
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}

		//  Establecer la conexión con la base de datos
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
			// Insertar una fila en la tabla
			System.out.println("Inserta nombre");
			String nombre = sc.nextLine();
			System.out.println("Inserta articulo");
			String articulo = sc.nextLine();
			System.out.println("Inserta el nif");
			String nif = sc.nextLine();
			insertarFila(connection, nombre, articulo,nif);

			// Listar toda la tabla
			listarTabla(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void insertarFila(Connection connection, String nombre, String valor,String nif) throws SQLException {
		String sql = "INSERT INTO clientes (nombre_cliente, articulo, nif) VALUES (?, ?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, nombre);
			preparedStatement.setString(2, valor);
			preparedStatement.setString(3, nif);
			preparedStatement.executeUpdate();
			
			System.out.println("Fila insertada con éxito en la base de datos.");
		}
	}

	private static void listarTabla(Connection connection) throws SQLException {
		String sql = "SELECT * FROM clientes";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				String nombre = resultSet.getString("nombre_cliente");
				String valor = resultSet.getString("articulo");
				String nif = resultSet.getString("nif");
				System.out.println("Nombre: " + nombre + ", Articulo: " + valor + ", Nif: "+nif );
			}
		}
	}

}
