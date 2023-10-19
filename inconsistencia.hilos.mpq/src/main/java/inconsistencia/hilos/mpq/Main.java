package inconsistencia.hilos.mpq;

public class Main {

	public static void main(String[] args) {
		Hilos hilo = new Hilos("Primero");
		Hilos hilo2 = new Hilos("Segundo");
		Hilos hilo3 = new Hilos("Tercero");
		
		hilo.start();
		hilo2.start();
		hilo3.start();
	}
}
