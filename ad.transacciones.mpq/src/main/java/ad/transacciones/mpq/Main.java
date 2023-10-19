package ad.transacciones.mpq;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.*;

public class Main {

	public static void main(String[] args) {

		try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:./pedidos.db")) {
			// Realizamos la conexion con la base de datos
			conexion.createStatement().execute("PRAGMA foreign_keys = ON");
			Statement stmt = conexion.createStatement();
			
			conexion.setAutoCommit(false);

			// PreparedStatement la linea introduciendo datos manualmente.

			String linea1 = "INSERT INTO pedidos (fecha,id_cliente,cantidad,id_articulo) VALUES (?,?,?,?)";
			String linea2 = "INSERT INTO existencias (descripcion,existencias,precio) VALUES (?,?,?)";
			String linea3 = "INSERT INTO envios (id_pedido,id_rider,terminado) VALUES (?,?,?)";
			try {
				try (PreparedStatement state = conexion.prepareStatement(linea1)) {
					
					state.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
					state.setInt(2, 1);
					state.setInt(3, 2);
					state.setInt(4, 1);
					state.executeUpdate();
					state.close();
				}
				try (PreparedStatement state = conexion.prepareStatement(linea2)) {
					
					state.setString(1, "Pizza");
					state.setInt(2, 4);
					state.setFloat(3, 10);
					state.executeUpdate();
					state.close();
				}
				try (PreparedStatement state = conexion.prepareStatement(linea3)) {
					constraint nombreCampo check (nombrecampo>0);
					state.setInt(1, 4);
					state.setInt(2, 1);
					state.setInt(3, 0);
					state.executeUpdate();
					state.close();
				}
			} catch (SQLException e) {
				conexion.rollback();
				e.printStackTrace();
			}
			conexion.commit();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
