package psp.transferencia.cliente.mpq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Main {

	public static void main(String[] args) {
		try {
			//serverAddress guarda la ip a la cual vamos a hacer la conexion.
			//En este caso 143.47.32.113 es la ip de mi servidor alojado en oracle.
			//Mi servidor usa el puerto 2002.
			String serverAddress = "143.47.32.113";
			int serverPort = 2002;

			// Archivo que se va a enviar.
			String filePath = "archivos.txt";

			// Crea un socket de cliente para conectarse al servidor.
			Socket socket = new Socket(serverAddress, serverPort);

			// Establece flujos de entrada y salida de datos.
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			// Lee el archivo linea por linea y envia al servidor.
			String line;
			while ((line = reader.readLine()) != null) {
				writer.write(line);
				writer.newLine();
			}

			System.out.println("Archivo enviado correctamente.");

			// Cierra los flujos y el socket.
			reader.close();
			writer.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}