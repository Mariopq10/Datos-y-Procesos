package ejemplos.ficheros.mpq;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Main {

	public static void main(String[] args) {

		String prov[] = { "Almeria", "Cadiz", "Malaga", "Jaen", "Cordoba" };
		try {
			// Create a FileWriter object
			// to write in the file
			BufferedWriter fichero = new BufferedWriter(new FileWriter("./provincias.txt"));
			// Writing into file
			// Note: The content taken above inside the
			// string
			for (String p : prov) {
				System.out.println(p.toString());
				fichero.write(p.toString());
				fichero.newLine();
			}
			// Printing the contents of a file
			// Closing the file writing connection
			fichero.close();
			// Display message for successful execution of
			// program on the console
			System.out.println("File is created successfully with the content.");
		}
		// Catch block to handle if exception occurs
		catch (IOException e) {
			// Print the exception
			System.out.print(e.getMessage());
		}

		String usuario = null;
		String contrasena = null;
		String servidor = null;
		int puerto = 0 ;
		// Creación de fichero de configuracion.
		Properties configuracion = new Properties();
		configuracion.setProperty("user", "variableUsuario");
		configuracion.setProperty("password", "variableContrasena");
		configuracion.setProperty("server", "variableServer");
		configuracion.setProperty("port", "25");
		try {
			configuracion.store(new FileOutputStream("configuracion.props"), "Fichero de configuracion");
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		// Lectura del fichero de configuracion
		// Usamos el mismo Properties.
		try {
			configuracion.load(new FileInputStream("configuracion.props"));
			usuario = configuracion.getProperty("user");
			contrasena = configuracion.getProperty("password");
			servidor = configuracion.getProperty("server");
			puerto = Integer.valueOf(configuracion.getProperty("port"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("" + usuario + contrasena + servidor + puerto);
	}
}
