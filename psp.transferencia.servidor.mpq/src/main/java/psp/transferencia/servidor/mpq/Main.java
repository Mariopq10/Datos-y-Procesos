package psp.transferencia.servidor.mpq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static void main(String[] args) {
		// En el ejercicio pone puerto 2023, en clase usabamos el 2002, se puede
		// modificar cambiando el valor de la variable puerto.
		int puerto = 2002;

		try {
			// Establezco el socket del servidor con el puerto 2002
			ServerSocket serverSocket = new ServerSocket(puerto);

			while (true) {
				Socket socket = serverSocket.accept();

				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new FileWriter("archivos.txt"));

				// Lee el archivo enviado por el cliente
				int tamano = 0;
				String linea;
				while ((linea = reader.readLine()) != null) {
					System.out.println("Recibido: " + linea);
					if (linea.length() != 0) {
						tamano += linea.length() + 2;
					} else if (linea.length() == 0) {
						tamano += 2;
					}
					writer.write(linea);
					writer.newLine();
				}
				System.out.println("Cliente conectado desde: " + socket.getInetAddress()
						+ " se ha transferido un total de " + tamano + " bytes");
				System.out.println("Archivo recibido correctamente.");

				// Cierra los flujos y el socket
				reader.close();
				writer.close();
				socket.close();
				serverSocket.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}