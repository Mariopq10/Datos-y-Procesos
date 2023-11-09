package psp.filosofos.mpq;

public class Filosofo extends Thread {
	private Mesa mesa;
	private byte posicion;

	public Filosofo(Mesa mesa, byte posicion) {
		super();
		this.mesa = mesa;
		this.posicion = posicion;
	}

	public void pensar() {
		System.out.println("El filosofo en la posicion " + posicion + " esta pensando.");

		try {
			Thread.sleep((long) (Math.random() * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void comiendo() {
		System.out.println("El filosofo " + posicion + " esta comiendo.");

		try {
			Thread.sleep((long) (Math.random() * 4000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Al pasar por la funcion coger palillo, si alguno de los dos esta ocupado, el
	// hilo se queda esperando con el wait() que hay dentro de su funcion, cuando
	// otro hilo en la funcion soltarPalillo da el notify, despierta a los demas
	// hilos para seguir la ejecucion.

	@Override
	public void run() {
		while (true) {
			this.pensar();
			this.mesa.cogerPalillo(this.posicion);
			this.comiendo();
			this.mesa.soltarPalillo(this.posicion);
		}
	}

}
