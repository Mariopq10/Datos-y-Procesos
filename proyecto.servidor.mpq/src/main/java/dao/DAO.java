package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO {

	public void enviarListadoProductos(BufferedWriter writer, Connection connection) throws SQLException, IOException {
		List<Producto> productos = new ArrayList<>();
		try (PreparedStatement statement = connection
				.prepareStatement("SELECT nombre, cantidad, seccion FROM producto")) {
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					String nombre = resultSet.getString("nombre");
					int cantidad = resultSet.getInt("cantidad");
					int seccion = resultSet.getInt("seccion");
					Producto producto = new Producto(nombre, cantidad, seccion);
					productos.add(producto);
				}
			}
		}
		for (Producto producto : productos) {
			writer.write(
					producto.getNombre() + " - " + producto.getCantidad() + " - Seccion: " + producto.getSeccion());
			writer.newLine();
			writer.flush();
		}
	}

	public void enviarListadoProductosSeccion(BufferedWriter writer, BufferedReader reader, Connection connection)
			throws SQLException, IOException {
		
		List<Producto> productos = new ArrayList<>();
		writer.write("Ingrese la sección para ver los productos: \n");
		writer.flush();
		int seccion = Integer.parseInt(reader.readLine());
		String query = "SELECT nombre, cantidad FROM producto WHERE seccion = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setInt(1, seccion);
	        try (ResultSet resultSet = statement.executeQuery()) {
	            // Verificar si hay resultados en el ResultSet
	            if (!resultSet.isBeforeFirst()) {
	                // No hay resultados, la sección no existe
	                writer.write("No hay productos en la sección especificada.");
	                writer.newLine();
	                writer.flush();
	                return;
	            }
	            // Procesar resultados si la sección existe
	            while (resultSet.next()) {
	                String nombre = resultSet.getString("nombre");
	                int cantidad = resultSet.getInt("cantidad");
	                Producto producto = new Producto(nombre, cantidad, seccion);
	                productos.add(producto);
	            }
	        }
	    }
		writer.write("Listado de productos de la seccion."+seccion+"\n");
		writer.newLine();
		writer.flush();
	    // Enviar listado de productos si la sección existe
	    for (Producto producto : productos) {
	    	writer.write(producto.getNombre() + " - " + producto.getCantidad() + (producto.getCantidad() == 1 ? " unidad" : " unidades"));
	        writer.newLine();
	        writer.flush();
	    }
	}

	public void agregarProducto(BufferedWriter writer, BufferedReader reader, Connection connection) {
		try {
			writer.write("Dime el nombre del producto que deseas agregar\n");
			writer.flush();
			String nombre = reader.readLine();
			
			String query = "SELECT COUNT(*) FROM producto WHERE nombre = ?";
	        try (PreparedStatement countStatement = connection.prepareStatement(query)) {
	            countStatement.setString(1, nombre);
	            try (ResultSet resultSet = countStatement.executeQuery()) {
	                if (resultSet.next() && resultSet.getInt(1) > 0) {
	                    writer.write("El producto ya existe en el inventario.\n");
	                    writer.flush();
	                    return; // Salir del método si el producto ya existe
	                }
	            }
	        }
	        
			writer.write("Dime la cantidad del producto que deseas agregar\n");
			writer.flush();
			int cantidad = 0;
			int seccion = 0;
			try {
				cantidad = Integer.parseInt(reader.readLine());
				writer.write("Dime la seccion del producto\n");
				writer.flush();
				seccion = Integer.parseInt(reader.readLine());

			} catch (NumberFormatException e) {
				// Manejar el caso en que el usuario ingresa algo que no es un número entero
				writer.write("Error: Por favor, ingresa un número entero válido.\n");
				writer.flush();
				return;
			}

			String sql = "INSERT INTO producto (nombre, cantidad, seccion) VALUES (?, ?, ?)";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, nombre);
				statement.setInt(2, cantidad);
				statement.setInt(3, seccion);
				int filasInsertadas = statement.executeUpdate();
				if (filasInsertadas > 0) {
					System.out.println("Producto agregado exitosamente a la seccion " + seccion);
				} else {
					System.out.println("No se pudo agregar el producto al inventario");
				}
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	public void actualizarInventario(BufferedWriter writer, BufferedReader reader, Connection connection) {
		try {
			// Solicitar el nombre del producto a actualizar
			writer.write("Ingrese el nombre del producto que desea actualizar: \n");
			writer.newLine();
			writer.flush();
			String nombreProducto = reader.readLine();

			// Verificar si el producto existe en la base de datos
			String query = "SELECT * FROM producto WHERE nombre = ?";
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, nombreProducto);
				try (ResultSet resultSet = statement.executeQuery()) {
					if (!resultSet.next()) {
						writer.write("El producto especificado no existe en el inventario.");
						writer.newLine();
						writer.flush();
						return;
					}
				}
			}

			// El producto existe en la base de datos, continuar con la actualización
			// Solicitar la nueva cantidad del producto
			writer.write("Ingrese la nueva cantidad del producto:\n");
			writer.flush();
			int nuevaCantidad = Integer.parseInt(reader.readLine());

			// Solicitar al usuario si desea modificar la sección del producto
			writer.write("¿Desea modificar la sección del producto? (S/N): \n");
			writer.flush();
			String modificarSeccion = reader.readLine();

			// Verificar si el usuario desea modificar la sección del producto
			if (modificarSeccion.equalsIgnoreCase("S")) {
				// Solicitar la nueva sección del producto
				writer.write("Ingrese la nueva sección del producto: \n");
				writer.flush();
				int nuevaSeccion = Integer.parseInt(reader.readLine());

				// Actualizar la sección del producto en la base de datos
				String sqlUpdateSeccion = "UPDATE producto SET seccion = ? WHERE nombre = ?";
				try (PreparedStatement statement = connection.prepareStatement(sqlUpdateSeccion)) {
					statement.setInt(1, nuevaSeccion);
					statement.setString(2, nombreProducto);
					int filasActualizadas = statement.executeUpdate();
					if (filasActualizadas > 0) {
						writer.write("Sección del producto actualizada correctamente.");
						writer.newLine();
						writer.flush();
					} else {
						writer.write("No se pudo actualizar la sección del producto.");
						writer.newLine();
						writer.flush();
					}
				}
			} else if (modificarSeccion.equalsIgnoreCase("N")) {
				// El usuario no desea modificar la sección del producto
				writer.write("No se realizaron cambios en la sección del producto.");
				writer.newLine();
				writer.flush();
			} else {
				// Opción inválida
				writer.write("Opción inválida. Por favor, seleccione S para modificar o N para no modificar.");
				writer.newLine();
				writer.flush();
			}

			// Actualizar la cantidad del producto en la base de datos
			String sql = "UPDATE producto SET cantidad = ? WHERE nombre = ?";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setInt(1, nuevaCantidad);
				statement.setString(2, nombreProducto);
				int filasActualizadas = statement.executeUpdate();
				if (filasActualizadas > 0) {
					writer.write("Inventario actualizado correctamente para el producto: " + nombreProducto);
					writer.newLine();
					writer.flush();
				} else {
					writer.write("No se pudo actualizar el inventario para el producto: " + nombreProducto);
					writer.newLine();
					writer.flush();
				}
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

}
