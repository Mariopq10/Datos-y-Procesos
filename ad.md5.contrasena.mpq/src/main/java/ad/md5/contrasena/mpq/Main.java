package ad.md5.contrasena.mpq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import ad.md5.contrasena.mpq.Md5;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// Establecemos la conexion con la base de datos.
		try (Connection conexion = DriverManager.getConnection("jdbc:mysql://192.168.6.221:3306/mario", "mario",
				"12345")) {
			Statement stmt = conexion.createStatement();
			// Establecemos el fichero.txt del que se recogeran los datos de lectura de
			// usuarios.
			File file = new File(".//fichero.txt");
			BufferedReader bufferLectura = new BufferedReader(new FileReader(file));

			// String linea almacenara la linea que leera el bufferLectura.readLine()
			String linea;

			// Leo el texto plano del archivo fichero.txt en un bucle que recogera los datos
			// de cada linea, encriptara la contrasena y la almacenara en la base de datos
			System.out.println(
					"1-Leer fichero e insertar datos en la base de datos.\n2-Login del usuario.\n3-Exportar tabla a un fichero de texto");

			byte opcion = Byte.parseByte(sc.nextLine());
			switch (opcion) {
			case 1:
				while ((linea = bufferLectura.readLine()) != null) {
					// Leo el texto plano del archivo fichero.txt en un bucle que recogera los datos
					// de cada linea, encriptara la contrasena y la almacenara en la base de datos
					String[] contenido = linea.split(",");
					String nombreLogin = contenido[0];
					String contrasena = contenido[1];
					String nombreCompleto = contenido[2];
					System.out.println("Nombre: " + nombreLogin + ", Contraseña: " + contrasena);
					String lineaInsert = "INSERT INTO clientecontrasena (nombre_login,contrasena,nombrecompleto) VALUES (?,?,?)";
					// Almacenamos en una variable String la contrasena cifrada a traves de la
					// funcion cifrarConMD5
					String contrasenaCifrada = Md5.cifrarConMD5(contrasena);

					try (PreparedStatement state = conexion.prepareStatement(lineaInsert)) {
						state.setString(1, nombreLogin);
						state.setString(2, contrasenaCifrada);
						state.setString(3, nombreCompleto);
						state.executeUpdate();
						state.close();
					}
				}
				bufferLectura.close();
				break;

			case 2:
				System.out.println("Inserta el nick del cliente: ");
				String nombreLogin = sc.nextLine();
				System.out.println("Inserta la contrasena: ");
				String contrasena = sc.nextLine();
				contrasena = Md5.cifrarConMD5(contrasena);
				String lineaLogin = "SELECT nombre_login,contrasena FROM clientecontrasena";
				try (PreparedStatement state = conexion.prepareStatement(lineaLogin)) {
					ResultSet rs2 = stmt.executeQuery(lineaLogin);
					while (rs2.next())
						if (rs2.getString(1).equals(nombreLogin) && rs2.getString(2).equals(contrasena)) {
							System.out.println("Login con usuario : " + nombreLogin + " correcto.");
						}
				} catch (Exception e) {
					System.err.println("Error");
					e.printStackTrace();
				}

				break;
			case 3:
				//Exportacion de la base de datos en un archivo de texto.
				String lineaLectura = "SELECT * FROM clientecontrasena";
				File archivoExport = new File(".//exportacion.txt");
				BufferedWriter bufferEscritura = new BufferedWriter(new FileWriter(archivoExport));
				ResultSet rs2 = stmt.executeQuery(lineaLectura);
				while (rs2.next()) {
					String texto = rs2.getString(1) + "," + rs2.getString(2) + "," + rs2.getString(3);
					bufferEscritura.write(texto + "\n");
				}
				bufferEscritura.close();
				break;

			}// Fin de switch

		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
