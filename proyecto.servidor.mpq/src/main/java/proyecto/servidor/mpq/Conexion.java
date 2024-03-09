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

import dao.Usuario;
import logger.CustomLogger;
import dao.DAO;
import dao.Producto;

public class Conexion {

	private int puerto = 2024;
	private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/inventario";
	private static final String MYSQL_USER = "root";
	private static final String MYSQL_PASSWORD = "";
	private CustomLogger log = new CustomLogger();
	private DAO dao = new DAO();
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

			if (autenticar(reader, writer, connection, socket)) {

				// Continuar con la lógica del sistema después del inicio de sesión
				mostrarMenuPrincipal(writer);

				// Leer las selecciones del cliente y procesarlas
				String opcion;
				while ((opcion = reader.readLine()) != null) {
					switch (opcion) {
					case "1":
						dao.enviarListadoProductos(writer, connection);
						
						break;
					case "2":
						//Mostrar productos por seccion
						dao.enviarListadoProductosSeccion(writer, reader, connection);
						
						break;
					case "3":
						dao.actualizarInventario(writer, reader, connection);

						break;
					case "4":
						dao.agregarProducto(writer, reader, connection);

						break;
					case "5":
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
		menu.append("Opciones:\n")
				.append("1. Listar Inventario\n").append("2. Mostrar por seccion\n")
				.append("3. Actualizar inventario\n").append("4. Añadir al inventario.\n").append("5. Salir\n").append("Seleccione una opción:");
		writer.write(menu.toString());
		writer.newLine();
		writer.flush();
	}

	private boolean autenticar(BufferedReader reader, BufferedWriter writer, Connection connection, Socket socket) {
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
				if (verificarCredenciales(username, Md5.cifrarConMD5(password), connection)) {
					// Inicio de sesión exitoso
					writer.write("¡Inicio de sesión exitoso! Bienvenido, " + username);
					writer.newLine();
					writer.flush();
					CustomLogger.logEvent("Usuario " + username + " logado con exito " + socket.getInetAddress());

					return true;
				} else {
					// Inicio de sesión fallido
					writer.write("¡Inicio de sesión fallido! Nombre de usuario o contraseña incorrectos.");
					writer.newLine();
					writer.flush();
					CustomLogger.logEvent(
							"Usuario " + username + " intento de conexion incorrecto " + socket.getInetAddress());
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	private boolean verificarCredenciales(String username, String password, Connection connection) {
		try {
			String query = "SELECT * FROM usuarios WHERE nombre = ? AND password = ?";
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
	

}
