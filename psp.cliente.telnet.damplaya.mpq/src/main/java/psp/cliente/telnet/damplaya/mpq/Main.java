package psp.cliente.telnet.damplaya.mpq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String serverAddress = "damplaya.hopto.org";
		int serverPort = 110;

		// Crea un socket de cliente para conectarse al servidor.
		Socket socket;
		try {
			socket = new Socket(serverAddress, serverPort);

			// Establece flujos de entrada y salida de datos.
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			String conexion = reader.readLine();
			System.out.println(conexion);
			Boolean error;
			if (conexion.contains("+")) {

				do {
					error = false;
					System.out.println("Dime el nombre de usuario");
					String usuario = "user " + sc.nextLine();
					writer.write(usuario + "\r\n");
					writer.flush();

					String respuesta = reader.readLine();
					System.out.println(respuesta);
					if (respuesta.contains("-")) {
						error = true;
					}

					System.out.println("Introduce contrasena");
					String pass = "pass " + sc.nextLine();
					writer.write(pass + "\r\n");
					writer.flush();

					String respuesta2 = reader.readLine();
					System.out.println(respuesta2);
					if (respuesta2.contains("ERR")) {
						error = true;
					}
				} while (error);

				System.out.println("Que desea hacer");

				Funciones.displayMenu(reader, writer);


			} else {
				System.out.println("Conexion erronea");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
