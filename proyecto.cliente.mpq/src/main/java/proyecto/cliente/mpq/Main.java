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
		 try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
		         BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		         BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in))) {

		        System.out.println("Conectado al servidor. Puede comenzar a enviar mensajes:");

		        // Thread para recibir mensajes del servidor
		        Thread receiveThread = new Thread(() -> {
		            try {
		                String serverResponse;
		                while ((serverResponse = reader.readLine()) != null) {
		                    System.out.println(serverResponse);
		                }
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        });
		        receiveThread.start();

		        // Thread para enviar mensajes al servidor
		        Thread sendThread = new Thread(() -> {
		            try {
		                String userInput;
		                while ((userInput = userInputReader.readLine()) != null) {
		                    writer.write(userInput + "\n");
		                    writer.flush();
		                }
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        });
		        sendThread.start();

		        // Esperar a que ambos hilos terminen
		        receiveThread.join();
		        sendThread.join();

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
}