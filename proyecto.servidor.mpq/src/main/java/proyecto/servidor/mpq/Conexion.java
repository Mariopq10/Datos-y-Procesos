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

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import dao.Cliente;
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
	    try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

	        if (autenticar(reader,writer,connection)) {

	            // Continuar con la lógica del sistema después del inicio de sesión
	            mostrarMenuPrincipal(writer);

	            // Leer las selecciones del cliente y procesarlas
	            String opcion;
	            while ((opcion = reader.readLine()) != null) {
	                switch (opcion) {
	                    case "1":
	                        enviarListadoProductos(writer, connection);
	                        break;
	                    case "2":
	                        // Lógica para comprar productos
	                        break;
	                    case "3":
	                        writer.write("Dime el nombre del producto que deseas agregar\n");
	                        writer.flush();
	                        String nombre = reader.readLine();
	                        writer.write("Dime la cantidad del producto que deseas agregar\n");
	                        writer.flush();
	                        int cantidad = 0;
	                        try {
	                            cantidad = Integer.parseInt(reader.readLine());
	                            agregarProducto(nombre, cantidad);
	                        } catch (NumberFormatException e) {
	                            // Manejar el caso en que el usuario ingresa algo que no es un número entero
	                            writer.write("Error: Por favor, ingresa un número entero válido.\n");
	                            writer.flush();
	                        }
	                        
	                        break;
	                    case "4":
	                        writer.write("Desconexión realizada\n");
	                        writer.flush();
	                        socket.close();
	                        return;
	                    default:
	                        writer.write("Opción inválida, prueba otra vez.\n");
	                        writer.flush();
	                        break;
	                }
	                mostrarMenuPrincipal(writer);
	            }
	        } else {
	            writer.write("¡Inicio de sesión fallido! Nombre de usuario o contraseña incorrectos.");
	            writer.newLine();
	            writer.flush();
	            socket.close();
	        }
	    } catch (IOException | SQLException e) {
	        e.printStackTrace();
	    }
	}

	private void mostrarMenuPrincipal(BufferedWriter writer) throws IOException {
	    StringBuilder menu = new StringBuilder();
	    menu.append("Bienvenido al sistema de gestión de productos.\n")
	            .append("Opciones:\n")
	            .append("1. Listar productos\n")
	            .append("2. Comprar productos\n")
	            .append("3. Ingresar producto\n")
	            .append("4. Salir\n")
	            .append("Seleccione una opción:");
	    writer.write(menu.toString());
	    writer.newLine();
	    writer.flush();
	}

	private boolean autenticar(BufferedReader reader, BufferedWriter writer, Connection connection) {
	    while (true) {
	        try {
	            // Solicitar nombre de usuario
	            writer.write("Por favor, ingrese su nombre de usuario:");
	            writer.newLine();
	            writer.flush();
	            String username = reader.readLine();

	            // Solicitar contraseña
	            writer.write("Por favor, ingrese su contraseña:");
	            writer.newLine();
	            writer.flush();
	            String password = reader.readLine();

	            // Verificar credenciales en la base de datos
	            if (verificarCredenciales(username, password, connection)) {
	                // Inicio de sesión exitoso
	                writer.write("¡Inicio de sesión exitoso! Bienvenido, " + username);
	                writer.newLine();
	                writer.flush();
	                return true;
	            } else {
	                // Inicio de sesión fallido
	                writer.write("¡Inicio de sesión fallido! Nombre de usuario o contraseña incorrectos.");
	                writer.newLine();
	                writer.flush();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	}


	
	private boolean verificarCredenciales(String username, String password, Connection connection) {
	    try {
	        String query = "SELECT * FROM clientes WHERE nombre = ? AND password = ?";
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setString(1, username);
	            statement.setString(2, password);
	            try (ResultSet resultSet = statement.executeQuery()) {
	                return resultSet.next(); // Si hay un resultado, las credenciales son válidas
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
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