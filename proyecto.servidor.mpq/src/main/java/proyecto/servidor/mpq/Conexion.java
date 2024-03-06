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
import java.sql.PreparedStatement;
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

			StringBuilder respuestaPrincipal = new StringBuilder();
			respuestaPrincipal.append("Bienvenido al sistema de gestión de productos.\n").append("Opciones:\n")
					.append("1. Listar productos\n").append("2. Comprar productos\n").append("3. Salir\n");

			// Enviar la respuesta principal al cliente
			writer.write(respuestaPrincipal.toString());
			writer.newLine(); // Añadir una nueva línea al final para indicar el final del mensaje
			writer.flush();

			// Leer todas las líneas enviadas por el cliente
			String opcion;
			while ((opcion = reader.readLine()) != null) {
				System.out.println("Cliente selecciona " + opcion);
				switch (opcion) {
				case "1":
					enviarListadoProductos(writer, connection);
					break;
				case "2":
					// Lógica para comprar productos
					writer.write("Opción no implementada: Comprar productos\n");
					writer.flush();
					break;
				case "3":
				    // Agregar producto
				    writer.write("Ingrese el nombre del producto y la cantidad separados por espacio (Ejemplo: Fanta 5):");
				    writer.newLine();
				    writer.flush();
				    String datosProducto = reader.readLine();
				    System.out.println(datosProducto);
				    String[] partes = datosProducto.split(" ");
				    if (partes.length == 2) {
				        String nombreProducto = partes[0];
				        int cantidadProducto = Integer.parseInt(partes[1]);
				        agregarProducto(nombreProducto, cantidadProducto); // Agregar producto a la base de datos
				        System.out.println(nombreProducto + " " +cantidadProducto);
				        writer.write("Producto agregado exitosamente");
				    } else {
				        writer.write("Formato inválido. Por favor, ingrese el nombre del producto y la cantidad separados por espacio.");
				    }
				    writer.newLine();
				    writer.flush();
				    break;
				case "4":
					// Salir
					writer.write("Desconexion realizada\n");
					writer.flush();
					socket.close();
					return; // Salir del método después de cerrar la conexión
				default:
					// Opción inválida
					writer.write("Opción inválida\n");
					writer.flush();
					break;
				}
			}

			// Si el cliente cierra la conexión, llegará a este punto
			System.out.println("Cliente desconectado");
			socket.close();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void enviarListadoProductos(BufferedWriter writer, Connection connection) throws SQLException, IOException {
		List<Producto> productos = new ArrayList<>();
		try (PreparedStatement statement = connection.prepareStatement("SELECT nombre, cantidad FROM producto")) {
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					String nombre = resultSet.getString("nombre");
					int cantidad = resultSet.getInt("cantidad");
					Producto producto = new Producto(nombre, cantidad);
					productos.add(producto);
				}
			}
		}
		for (Producto producto : productos) {
			writer.write(producto.getNombre() + " - " + producto.getCantidad());
			writer.newLine();
			writer.flush();
		}
	}

	private void agregarProducto(String nombre, int cantidad) {
	    try (Connection connection = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD)) {
	        String sql = "INSERT INTO producto (nombre, cantidad) VALUES (?, ?)";
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setString(1, nombre);
	            statement.setInt(2, cantidad);
	            int filasInsertadas = statement.executeUpdate();
	            if (filasInsertadas > 0) {
	                System.out.println("Producto agregado exitosamente");
	            } else {
	                System.out.println("No se pudo agregar el producto");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}