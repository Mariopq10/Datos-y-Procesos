package salida.entrada.mpq;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.print.DocFlavor.INPUT_STREAM;

public class Main {

	public static void main(String[] args) {
		// Creando un proceso con process builder, hacemos un listado de un directorio
		// del usuario, como p ej descargas.
		// El objetivo es conseguir que el flujo de salida del proceso hijo se envie al
		// flujo de entrada del proceso padre

		Process proceso = null;
		ProcessBuilder descargas = new ProcessBuilder("cmd.exe", "/c", "dir", "C:\\Users\\DAM2\\Downloads");

		try {
			proceso = descargas.start();
			InputStream std = proceso.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(std));
			String linea;

			System.out.println("Esto lo escribe el proceso padre:");

			while ((linea = br.readLine()) != null) {
				System.out.println(linea);
			}
			br.close();
			proceso.waitFor();

		} catch (IOException ex) {
			System.err.println("Error");
			System.exit(-1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
