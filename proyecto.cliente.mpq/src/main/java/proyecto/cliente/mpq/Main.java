package proyecto.cliente.mpq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {

	private static final String SERVER_IP = "127.0.0.1"; // Cambia esto si el servidor está en una dirección diferente
	private static final int SERVER_PORT = 2024;

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in);
				Socket socket = new Socket(SERVER_IP, SERVER_PORT);
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

			// Leer la respuesta principal del servidor
			String respuestaPrincipal;
			while ((respuestaPrincipal = reader.readLine()) != null) {
				System.out.println(respuestaPrincipal);
				if (respuestaPrincipal.isEmpty()) {
					break;
				}
			}

			while (true) {
				String solicitud = sc.nextLine();
				writer.write(solicitud + "\n");
				writer.flush();

				String respuestaServidor;
				while ((respuestaServidor = reader.readLine()) != null) {
					System.out.println("Respuesta del servidor: " + respuestaServidor);
					if (respuestaServidor.isEmpty()) {
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
