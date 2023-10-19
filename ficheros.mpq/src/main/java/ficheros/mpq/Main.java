package ficheros.mpq;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.plaf.basic.BasicButtonListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Main {

	public static void main(String[] args) {
		/*
		 * Creamos dos clientes para usar posteriormente sus datos dentro del archivo de
		 * texto
		 */
		Cliente cliente1 = new Cliente("Mario", "Perez", (byte) 27);
		Cliente cliente2 = new Cliente("Ricardo", "Delgado", (byte) 27);
		/*
		 * Creamos el archivo usando la clase File y le damos un nombre, comprobamos
		 * posteriormente si hay un archivo existente con ese nombre, y en el catch
		 * recogemos un posible error.
		 */
		try {
			File lista1 = new File("archivo1.txt");
			if (lista1.createNewFile()) {
				System.out.println("Archivo creado: " + lista1.getName());
			} else {
				System.out.println("El archivo ya existe.");
			}
		} catch (IOException e) {
			System.out.println("Hay un error en la creacion del archivo.");
			e.printStackTrace();
		}
		/*
		 * Usamos la clase FileWriter para iniciar la escritura, usamos el metodo .write
		 * para introducir dentro del archivo de texto el toString de la clase Cliente,
		 * en este caso introducimos solamente los datos del cliente1.
		 */
		try {
			FileWriter myWriter = new FileWriter("archivo1.txt");
			myWriter.write(cliente1.toString());
			myWriter.close();
			System.out.println("Se ha modificado el archivo");
		} catch (IOException e) {
			System.out.println("Ha ocurrido un error en la modificación del archivo de texto.");
			e.printStackTrace();
		}

		try {
			File file = new File(".//libreria.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document listado = dBuilder.parse(file);
			NodeList nList = listado.getElementsByTagName("Author");
			System.out.println("Cantidad de autores = " + nList.getLength());
			for (byte i = 0; i < nList.getLength(); i++) {
				String item = nList.item(i).getTextContent();
				System.out.println(item);
			}

		} catch (Exception e) {
			System.err.println("Error");
		}
	}

}
