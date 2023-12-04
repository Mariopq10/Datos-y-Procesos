package psp.cliente.mpq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(2002);
			System.out.println("Servidor esperando conexiones en el puerto 2002...");

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Conexión establecida desde " + clientSocket.getInetAddress());

				Thread clientThread = new Thread(() -> {
					try {
						handleClient(clientSocket);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});

				clientThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void handleClient(Socket clientSocket) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

		// Fase de Identificación
		String identificacion = reader.readLine();
		if (identificacion.toLowerCase().startsWith("soy ")) {
			String nombre = identificacion.substring(4).trim();

			// Verificar si el nombre existe en el archivo
			List<String> lista = Files.readAllLines(Paths.get("nombres.txt"));
			if (lista.contains(nombre)) {
				writer.println("Bienvenido al sistema. Fase de registro.");

			} else {
				writer.println("Nombre desconocido. Cierre de conexion.");
			}
		} else {
			writer.println("Comando de identificación no reconocido. Conexion cerrada.");
		}

		clientSocket.close();
	}


}
