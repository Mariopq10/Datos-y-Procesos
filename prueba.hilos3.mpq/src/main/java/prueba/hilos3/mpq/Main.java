package prueba.hilos3.mpq;

public class Main {

	public static void main(String[] args) {

		// Se crea un objeto Raton
		Raton tinky = new Raton("Tinky", 5);

		// Creamos 3 hilos diferentes para el mismo objeto.
		Thread hilo = new Thread(tinky);
		Thread hilo2 = new Thread(tinky);
		Thread hilo3 = new Thread(tinky);
		// Ejecutamos los 3 hilos distintos del mismo objeto.
		System.out.println("Estado del hilo " + hilo.getState());
		hilo.start();
		System.out.println("Estado del hilo " + hilo.getState());
		hilo2.start();
		hilo3.start();
		// Creamos un bucle while en el que mostraremos el estado de uno de los hilos
		// ejecutados, este bucle terminará cuando su estado sea TERMINATED
		// una vez que termine el hilo, se mostrara el estado TERMINATED.
		while (hilo.getState() != Thread.State.TERMINATED) {

			System.out.println(hilo.getState());
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Estado del hilo " + hilo.getState());
	}

}
