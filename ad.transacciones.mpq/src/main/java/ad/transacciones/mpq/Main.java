package ad.transacciones.mpq;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:./pedidos.db")) {
			
			// Realizamos la conexion con la base de datos
			//Usamos el PRAGMA para que respete las foreign keys.
			conexion.createStatement().execute("PRAGMA foreign_keys = ON");
			Statement stmt = conexion.createStatement();

			conexion.setAutoCommit(false);
			// constraint nombreCampo check (nombrecampo>0) esta constraint la usamos en la base de datos sqlite para que las existencias cuando se pida un articulo sea mayor a 0.
			// PreparedStatement la linea introduciendo datos manualmente.

			String linea1 = "INSERT INTO pedidos (fecha,id_cliente,cantidad,id_articulo) VALUES (?,?,?,?)";
			String linea2 = "INSERT INTO articulos (descripcion,existencias,precio) VALUES (?,?,?)";
			String linea3 = "INSERT INTO envios (id_pedido,id_rider,terminado) VALUES (?,?,?)";
			String lineaUpdate = "UPDATE articulos set existencias = existencias - ? WHERE id_articulo = ?";
			
			//Aqui pedimos la cantidad de articulo id=1 para realizar pedidos y comprobaciones.
			System.out.println("Cuantas pizzas quieres");
			int nPizzas = Integer.parseInt(sc.nextLine());
			try {
				
				//Insert de la tabla pedidos con la cantidad de pizzas anteriores "nPizzas".
				try (PreparedStatement state = conexion.prepareStatement(linea1)) {
					state.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
					state.setInt(2, 1);
					state.setInt(3, nPizzas);
					state.setInt(4, 1);
					state.executeUpdate();
					state.close();
					System.out.println("Pedido realizado");

				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("No existe el articulo");
					conexion.rollback();
				}
				//Update tabla articulos comprobacion usando el String lineaUpdate.
				try (PreparedStatement state = conexion.prepareStatement(lineaUpdate)) {
					state.setInt(1, nPizzas);
					state.setInt(2, 1);
					state.executeUpdate();
					state.close();
				} catch (SQLException e) {
					System.err.println("No hay existencias");
					conexion.rollback();
				}
				
				//Insert de la tabla articulos.
				/*try (PreparedStatement state = conexion.prepareStatement(linea2)) {
					state.setString(1, "Pizza");
					state.setInt(2, nPizzas);
					state.setFloat(3, 10);
					state.executeUpdate();
					state.close();
				}*/
				//Insert de la tabla envios.
				
				try (PreparedStatement state = conexion.prepareStatement(linea3)) {

					state.setInt(1, 1);
					state.setInt(2, 1);
					state.setInt(3, 0);
					state.executeUpdate();
					state.close();
				} catch (SQLException e) {
					conexion.rollback();
				}

			} catch (SQLException e) {
				conexion.rollback();
				e.printStackTrace();
			}
			//Aqui realizamos el commit tras hacer todas las comprobaciones anteriores cada vez que se inserta o actualiza las tablas.
			conexion.commit();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}