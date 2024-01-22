package psp.ftp.cliente.mpq;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import org.apache.commons.net.ftp.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		FTPClient ftpCliente = new FTPClient();

		try {
			// Realizamos la conexion al servidor FTP
			ftpCliente.connect("damplaya.hopto.org", 21);
			ftpCliente.login("mario", "mario");
			// activamos el modo pasivo
			ftpCliente.enterLocalPassiveMode();

			//
			int opcion = -1;
			while (opcion != 0) {
				Funciones.menu();
				opcion = sc.nextInt();
				switch (opcion) {
				case 1:
					Funciones.listar(ftpCliente);
					break;
				case 2:
					System.out.print("Insercion de archivo.\nNombre del archivo a subir: ");
					String nombreArchivo = sc.next();
					File fileToUpload = new File(nombreArchivo);
					try {
						Funciones.subirFichero(ftpCliente, fileToUpload);
					} catch (IOException e) {
						System.err.println("Error al insertar el archivo.");
					}

					break;
				case 3:
					// Descargar un archivo del servidor FTP
					try {
						System.out.print("Insercion de archivo.\nInserte el nombre:");
						String archivoABajar = sc.next();
						System.out.print("Inserte el nombre y ruta de como lo quieres llamar:");
						String nuevoArchivo = sc.next();
						Funciones.descargarFichero(ftpCliente, archivoABajar, nuevoArchivo);
					} catch (IOException e) {
						System.err.println("Error al descargar un archivo.");
					}
					break;
				}
			}

			// Desconectar
			ftpCliente.logout();
			ftpCliente.disconnect();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
