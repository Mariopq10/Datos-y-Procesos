package prueba.hilos2.mpq;

public class Main {

	public static void main(String[] args) {
		
		//Se crea un objeto Raton
		Raton tinky = new Raton("Tinky", 5);
		
		//Creamos 3 hilos diferentes para el mismo objeto.
		Thread hilo = new Thread(tinky);
		Thread hilo2 = new Thread(tinky);
		Thread hilo3 = new Thread(tinky);
		//Ejecutamos los 3 hilos distintos del mismo objeto.
		hilo.start();
		hilo2.start();
		hilo3.start();

	}

}
