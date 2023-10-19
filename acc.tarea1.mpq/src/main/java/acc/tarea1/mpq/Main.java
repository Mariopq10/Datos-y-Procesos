package acc.tarea1.mpq;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Main {

	public static void main(String[] args) {
		BufferedReader lector;
		ArrayList<String> nombres = new ArrayList();
		File lista = null;
		nombres.add("Mario");
		nombres.add("Pepe");
		nombres.add("Ricardo");
		String linea;
		byte contador = 0;

		try {
			lista = new File("listado.txt");
			if (lista.createNewFile()) {
				System.out.println("Archivo creado: " + lista.getName());
			} else {
				System.out.println("El archivo ya existe.");
			}
		} catch (IOException e) {
			System.err.println("Hay un error en la creacion del archivo.");
			e.printStackTrace();
		}
		try {
			FileWriter escritor = new FileWriter("listado.txt");
			for (byte i = 0; i < nombres.size(); i++) {
				escritor.write(nombres.get(i) + "\n");
			}
			escritor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			lector = new BufferedReader(new FileReader("listado.txt"));
			while ((linea = lector.readLine()) != null) {
				System.out.println(linea);
				contador++;
			}
			System.out.println(contador);
		} catch (IOException e) {
			System.err.println("Error.");
			e.printStackTrace();
		}
	}
}