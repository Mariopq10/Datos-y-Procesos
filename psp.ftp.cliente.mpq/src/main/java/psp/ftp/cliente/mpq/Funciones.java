package psp.ftp.cliente.mpq;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.net.ftp.FTPClient;

public class Funciones {

	public static void menu() {
		System.out.println("1) Listar directorio \n2) Subir un archivo \n3) Descargar un fichero \n0) Salir");
	}
	
	public static void listar(FTPClient ftpCliente) throws IOException {
		// Obtener el listado de archivos en el directorio remoto
		System.out.println("Listado de archivos en el directorio remoto:");
		String[] files = ftpCliente.listNames("/home/mario");
		System.out.println(Arrays.toString(files));
	}
	public static void subirFichero(FTPClient ftpClient, File file) throws IOException {
		try (FileInputStream fIS = new FileInputStream(file)) {
			if (ftpClient.storeFile(file.getName(), fIS)) {
				System.out.println("Archivo subido con exito");
			} else {
				System.out.println("No se pudo subir el archivo al servidor.");
			}
		}
	}

	public static void descargarFichero(FTPClient ftpClient, String remoteFileName, String localFileName) throws IOException {
		try (FileOutputStream fOS = new FileOutputStream(localFileName)) {
			ftpClient.retrieveFile(remoteFileName, fOS);
			System.out.println("Archivo descargado con exito");
		}
	}
	
}
