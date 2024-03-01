package proyecto.cliente.mpq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {

	private static final String SERVER_IP = "127.0.0.1"; // Cambia esto si el servidor está en una dirección diferente
    private static final int SERVER_PORT = 2024;
	
	public static void main(String[] args) {

		try (Scanner sc = new Scanner(System.in)) {
			 while (true){
			Socket socket = new Socket(SERVER_IP, SERVER_PORT);

			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			String solicitud = sc.nextLine();
;
			writer.write(solicitud + "\n");
			writer.flush();

			String respuestaServidor = reader.readLine();
			System.out.println("Respuesta del servidor: " + respuestaServidor);

			socket.close();
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}