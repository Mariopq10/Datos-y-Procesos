package psp.socket.mpq;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int portNumber = 12345;

		try {
			ServerSocket serverSocket = new ServerSocket(portNumber);
			while (true) {
				// Crea un socket de servidor

				System.out.println("El servidor está escuchando en el puerto " + portNumber);

				// Espera a que un cliente se conecte
				Socket clientSocket = serverSocket.accept();
				System.out.println("Cliente conectado desde: " + clientSocket.getInetAddress());

				// Lee los datos del cliente
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String data = in.readLine();
				System.out.println("Datos recibidos: " + data);
				PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
				output.println(obtenerFraseAleatoria("frases.txt"));
				// Cierra las conexiones
				in.close();
				clientSocket.close();
			}

			// serverSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String obtenerFraseAleatoria(String nombreArchivo) {
		List<String> lineas = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				lineas.add(linea);
			}

			if (!lineas.isEmpty()) {
				Random rand = new Random();
				return lineas.get(rand.nextInt(lineas.size())).trim();
			} else {
				return "El archivo está vacío.";
			}
		} catch (IOException e) {
			return "Error al leer el archivo: " + e.getMessage();
		}
	}

}
