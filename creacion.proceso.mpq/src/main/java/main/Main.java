package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) throws IOException {

		Process proceso = null;
		ArrayList<String> lista = new ArrayList();
		lista.add("notepad.exe C:\\\\Users\\\\DAM2\\\\eclipse-workspace\\\\creacion.proceso.mpq\\\\listado.txt");
		lista.add("C:\\Users\\DAM2\\eclipse-workspace\\creacion.proceso.mpq\\listado.txt");
		if (args.length <= 0) {
			System.err.println("Introducir programa a ejecutar por argumentos");
			System.exit(-1);
		}

		ProcessBuilder pb = new ProcessBuilder(args);
		ProcessBuilder chrome = new ProcessBuilder("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
				"iesplayamar.es");
		try {
			proceso = pb.start();
			Thread.sleep(100);
			
			int retorno = proceso.waitFor(); // El padre espera bloqueado hasta que el hijo finalice su ejecución,
												// volviendo inmediatamente si el hijo ha finalizado con
												// anterioridad o si alguien le interrumpe
			System.out.println("El proceso " + Arrays.toString(args) + " acabó con el valor " + retorno);
			//proceso = chrome.start();

		} catch (IOException ex) {
			System.err.println("Error");
			System.exit(-1);
		} catch (InterruptedException ex) {
			System.err.println("Error");
			System.exit(-1);
		}
	}
}
