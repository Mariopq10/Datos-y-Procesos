package sincronizacion.procesos.mpq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		/*
		 * En programación multiproceso, varios procesos se turnan la CPU para avanzar
		 * de forma aparentemente simultanea para el usuario
		 * 
		 * El sistema operativo cede un espacio de memoria independiente para cada
		 * proceso y se encarga de su gestión.
		 * 
		 * Si los procesos cooperan entre ellos para obtener un resultado, es trabajo
		 * del programador establecer esa comunicación y sincronización
		 * 
		 * 
		 * 
		 * Métodos mas importantes:
		 * 
		 * waitfor() El proceso padre espera que el proceso hijo finalize y obtiene su
		 * valor de salida destroy() ExitValue() valor de salida del proceso hijo
		 * getInputStream, outputStream: Obtener flujos de salida y entrada al proceso
		 * 
		 * 
		 * Ejercicio:
		 * 
		 * Programa java que acepte como argumento un comando o programa
		 * 
		 * Ejecutará como proceso hijo el comando que enviamos como argumento. [0]
		 * 
		 * Una vez finalizado mostrará el valor de salida del proceso hijo, que prodrá
		 * ser 0 o 1
		 * 
		 * Capturamos las excepciones
		 * 
		 * Exporta el proyecto a .jar ejecutable.
		 * 
		 * Probad la ejecución usando estos argumentos, explicando la salida:
		 * 
		 * ping de 1 datagrama a un host de internet CON echo ping de 1 datagrama a un
		 * host de internet SIN echo El block de notas un programa python donde se
		 * retorne 0 o 1 de forma aleatoria Este puede ser el codigo de python
		 * 
		 * import random
		 * 
		 * n=random.randint(0,1)
		 * 
		 * print ("numero es ",n)
		 * 
		 * exit (n)
		 */

		if (args.length <= 0) {
			System.err.println("Introducir programa a ejecutar por argumentos");
			System.exit(-1);
		}
		// Se ejecuta el cmd y se le pasa por cmd los argumentos que queremos meter
		// dentro del programaa.
		try {
			Process pb = Runtime.getRuntime().exec(args[0]);

			int retorno = pb.waitFor(); // El padre espera bloqueado hasta que el hijo finalice su ejecución,
										// volviendo inmediatamente si el hijo ha finalizado con
										// anterioridad o si alguien le interrumpe
			if (pb.exitValue() == 0) {
				System.out.println("Ping correcto");
			} else {
				System.err.println("Error");
			}
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
