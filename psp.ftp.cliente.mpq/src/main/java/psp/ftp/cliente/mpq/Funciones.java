package psp.ftp.cliente.mpq;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.net.ftp.FTPClient;

public class Funciones {

	public static void menu() {
		System.out.println("Menu: \n1) Listar directorio \n2) Subir un archivo \n3)bajar un fichero \n0) Salir");
	}
	
	public static void listar(FTPClient ftpCliente) throws IOException {
		// Obtener el listado de archivos en el directorio remoto
		System.out.println("Listado de archivos en el directorio remoto:");
		String[] files = ftpCliente.listNames("/home/mario");
		System.out.println(Arrays.toString(files));
	}
}
