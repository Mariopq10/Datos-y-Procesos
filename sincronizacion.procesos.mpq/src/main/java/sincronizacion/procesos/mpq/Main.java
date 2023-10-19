package sincronizacion.procesos.mpq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		if (args.length <= 0) {
			System.err.println("Introducir programa a ejecutar por argumentos");
			System.exit(-1);
		}
		//Se ejecuta el cmd y se le pasa por cmd los argumentos que queremos meter dentro del programaa.
		try {
			Process pb = Runtime.getRuntime().exec(args[0]);
			
			int retorno = pb.waitFor(); // El padre espera bloqueado hasta que el hijo finalice su ejecución,
												// volviendo inmediatamente si el hijo ha finalizado con
												// anterioridad o si alguien le interrumpe
			if(pb.exitValue()==0) {
				System.out.println("Ping correcto");
			}else {
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
