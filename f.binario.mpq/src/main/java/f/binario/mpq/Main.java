package f.binario.mpq;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		Productos producto1 = new Productos("Cable", 3, 100);
		Productos producto2 = new Productos("Monitor", 300, 15);
		Productos producto3 = new Productos("Ordenador", 1000, 10);

		ArrayList<Productos> lista = new ArrayList<Productos>();
		lista.add(producto1);
		lista.add(producto2);
		lista.add(producto3);

		//Serializar un objeto
		ObjectOutputStream output = null;
		try (FileOutputStream fos = new FileOutputStream(".\\fichero.dat")) {
			output = new ObjectOutputStream(fos);
			for (byte i = 0; i < lista.size(); i++) {
				output.writeObject(lista.get(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Deserializar un objeto.
		ObjectInputStream input;
		try {
			input = new ObjectInputStream(new FileInputStream(".\\fichero.dat"));
			for (int i = 0; i < lista.size(); i++) {
				System.out.println((Productos) input.readObject());
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}