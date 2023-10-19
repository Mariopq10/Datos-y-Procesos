package acc.directorio.mpq;

import java.io.File;
import java.io.FileFilter;

public class Main {

	public static void main(String[] args) {
		File directorio = new File("C:\\");
		File[] archivos = directorio.listFiles();
		// También podemos guardar el nombre de cada fichero en un array de String, y
		// usarlo como en el comentario que tenemos abajo. Es otra forma de obtener el
		// nombre de los ficheros y almacenarlos en una variable.
		String[] listado = directorio.list();

		for (short i = 0; i < listado.length; i++) {
			// Esta es otra manera de hacerlo, guardandao el nombre de cada archivo en un
			// Array de String
			// System.out.println(listado[i]+" "+directorio.isDirectory()+"
			// "+directorio.getName());

			// El metodo isDirectory muestra si el fichero es una carpeta o no y devuelve un
			// boolean.
			// El metodo canWrite muestra si se tiene permisos de escritura y devuelve un
			// boolean.
			// El metodo canRead muestra si se tienen permisos de lectura y devuelve un
			// boolean.
			// El metodo getFreeSpace muestra el numero de bytes disponibles del directorio.
			System.out.println(archivos[i].getName() + " Directorio:" + archivos[i].isDirectory() + " Escritura:"
					+ archivos[i].canWrite() + " Lectura:" + archivos[i].canRead());
		}

	}

}
