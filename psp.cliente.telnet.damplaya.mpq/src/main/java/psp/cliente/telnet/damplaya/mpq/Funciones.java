package psp.cliente.telnet.damplaya.mpq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class Funciones {

	public static void displayMenu(BufferedReader reader, BufferedWriter writer) throws IOException {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("Elige una opcion:");
			System.out.println("1. Listar");
			System.out.println("2. Comando Stat");
			System.out.println("3. Leer correo");
			System.out.println("4. Exit");

			int opcion = sc.nextInt();
			seleccion(opcion, reader, writer);
		}
	}

	public static void seleccion(int opcion, BufferedReader reader, BufferedWriter writer) throws IOException {
		Scanner sc = new Scanner(System.in);
		switch (opcion) {
		case 1:
			System.out.println("Listar correos");
			writer.write("list\r\n");
			writer.flush();

			String respuesta;
			while ((respuesta = reader.readLine()) != ".") {
				System.out.println(respuesta);
				if (respuesta == ".") {
					break;
				}
			}

			break;
		case 2:
			System.out.println("Mostrando datos");
			writer.write("stat\r\n");
			writer.flush();

			String respuesta2 = reader.readLine();
			System.out.println(respuesta2);
			break;
		case 3:
			System.out.println("Dime el correo que quieres leer");
			String eleccion = sc.nextLine();
			writer.write("retr " + eleccion + "\r\n");
			writer.flush();

			String respuesta3;
			while ((respuesta3 = reader.readLine()) != null) {
				System.out.println(respuesta3);
				if (respuesta3 == ".") {
					break;
				}
			}

			break;
		case 4:
			System.out.println("Saliendo del programa");
			System.exit(0);
			break;
		default:
			System.out.println("Elige una opcion correcta.");
		}
	}
}
