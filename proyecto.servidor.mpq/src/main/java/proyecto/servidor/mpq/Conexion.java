package proyecto.servidor.mpq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import dao.Producto;

public class Conexion {

	private int puerto = 2024;
	private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/tienda";
	private static final String MYSQL_USER = "root";
	private static final String MYSQL_PASSWORD = "";
	private Object lock = new Object();

	public void iniciarServidor() {
		try {
			// Establecemos el socket del servidor con el puerto especificado.
			ServerSocket serverSocket = new ServerSocket(this.puerto);

			try (Connection connection = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD)) {
				while (true) {
					// Esperar a que un cliente se conecte a nuestro servidor.
					Socket socket = serverSocket.accept();

					// Procesar la conexión en un hilo separado para manejar múltiples clientes
					// simultáneos.
					new Thread(() -> procesarConexion(socket, connection)).start();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void procesarConexion(Socket socket, Connection connection) {
	    try {
	        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

	        
	            writer.write("Bienvenido al sistema de gestión de productos.\nOpciones:\n1. Listar productos\n2. Comprar productos\n3. Salir");
	            writer.flush();
	        

	        // Leer todas las líneas enviadas por el cliente
	        String opcion;
	        while ((opcion = reader.readLine()) != null) {
	        	System.out.println("Cliente selecciona "+opcion);
	            switch (opcion) {
	                case "1":
	                    // Listar productos
	                    List<Producto> productos = obtenerListadoProductos(connection);
	                    // Enviar la lista de productos al cliente
	                    for (Producto producto : productos) {
	                        writer.write(producto.getNombre() + " - " + producto.getCantidad());
	                        writer.flush();
	                    }
	                    break;
	                case "2":
	                    // Lógica para comprar productos
	                    writer.write("Opción no implementada: Comprar productos");
	                    writer.flush();
	                    break;
	                case "3":
	                    // Salir
	                    writer.write("Desconexion realizada");
	                    writer.flush();
	                    socket.close();
	                    return; // Salir del método después de cerrar la conexión
	                default:
	                    // Opción inválida
	                    writer.write("Opción inválida");
	                    writer.flush();
	                    break;
	            }
	        }

	        // Si el cliente cierra la conexión, llegará a este punto
	        System.out.println("Cliente desconectado");
	        socket.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


	private List<Producto> obtenerListadoProductos(Connection connection) {
		List<Producto> productos = new ArrayList<>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT nombre, cantidad FROM producto");
			while (resultSet.next()) {
				String nombre = resultSet.getString("nombre");
				int cantidad = resultSet.getInt("cantidad");
				Producto producto = new Producto(nombre, cantidad);
				productos.add(producto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productos;
	}
}