package psp.examen.hilos.mpq;

public class Productor extends Thread {
	private Cola cola;
	private String nombre;

	public Productor(Cola cola, String nombre) {
		super();
		this.cola = cola;
		this.nombre = nombre;
	}
	//Funcion servir que hace que el productor haga una espera antes de servir un pedido
	public void servir() {
		System.out.println(this.nombre + " está sirviendo");
		try {
			Thread.sleep((long) (Math.random() * 3000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			this.servir();
			this.cola.servirPedido();
			this.cola.obtenerLista();
		}
	}

}
