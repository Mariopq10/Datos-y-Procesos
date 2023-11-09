package psp.examen.procesos.mpq;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Process proceso = null;
		Scanner sc = new Scanner(System.in);
		String sitio;
		if (args.length <= 0) {
			System.err.println("Introducir programa a ejecutar por argumentos");
			System.exit(-1);
		}

		// ProcessBuilder pb = new ProcessBuilder(args);
		// ProcessBuilder chrome = new ProcessBuilder("cmd.exe","ping 127.0.0.1");

		try {
			// Te pido la web que quieres pasar al programa.
			System.out.println("Dime la web que quieres abrir");
			sitio = sc.nextLine();
			//Se ejecuta el proceso donde realizamos el ping a la ip que tenemos por argumento en este caso 127.0.0.1
			proceso = Runtime.getRuntime().exec("ping " + args[0]);

			Thread.sleep(100);

			int retorno = proceso.waitFor(); // El padre espera bloqueado hasta que el hijo finalice su ejecución,
												// volviendo inmediatamente si el hijo ha finalizado con
												// anterioridad o si alguien le interrumpe
			if (retorno == 0) {
				// El ping al ser "correcto" y da el valor 0, inicia el proceso y abre la web de
				// playamar, en este caso no la abre porq no hay internet.
				ProcessBuilder chrome = new ProcessBuilder("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
						sitio);
				chrome.start();
			} else {
				// Escribimos el sitio dentro de un fichero.txt en el caso de que no se haya realizado comunicacion con la ip pasada por argumento.
				BufferedWriter fichero = new BufferedWriter(new FileWriter("./fichero.txt"));
				fichero.write(sitio);
				fichero.close();
			}

			proceso.destroy();
			System.out.println("El proceso " + Arrays.toString(args) + " acabó con el valor " + retorno);

		} catch (IOException ex) {
			System.err.println("Error");
			System.exit(-1);
		} catch (InterruptedException ex) {
			System.err.println("Error");
			System.exit(-1);
		}

	}

}
