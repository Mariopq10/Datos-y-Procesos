package psp.serv.tickets.cliente.mpq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		final String SERVER_IP = "localhost";
		final int SERVER_PORT = 2024;

		try (Scanner sc = new Scanner(System.in)) {
			// Conectar al servidor
			Socket socket = new Socket(SERVER_IP, SERVER_PORT);

			// Establecer flujos de entrada y salida
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			// Solicitar entrada del usuario después de conectarse al servidor
			System.out.print("Ingrese su solicitud: ");
			String solicitud = sc.nextLine();

			// Enviar la solicitud al servidor
			writer.write(solicitud + "\n");
			writer.flush();

			// Leer la respuesta del servidor
			String respuestaServidor = reader.readLine();
			System.out.println("Respuesta del servidor: " + respuestaServidor);

			// Cerrar la conexión
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
