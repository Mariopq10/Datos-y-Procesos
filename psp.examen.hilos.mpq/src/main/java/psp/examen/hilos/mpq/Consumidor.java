package psp.examen.hilos.mpq;

public class Consumidor extends Thread{
	private String nombre;
	private Cola cola;
	
	public Consumidor(String nombre, Cola cola) {
		super();
		this.nombre = nombre;
		this.cola = cola;
	}


	//Funcion esperar que hace que el consumidor espere antes de realizar un pedido
	public void esperar(String pedido) {
		System.out.println(this.nombre +" está esperando una "+pedido+ " en la barra.");
		try {
			Thread.sleep((long) (Math.random() * 3000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Funcion consumir que recibe el String articuloAleatorio pedido y muestra el pedido consumido despues de haberlo consumido.
	public void consumir(String pedido) {
		System.out.println(this.nombre + " ha recibido el articulo "+pedido);

		try {
			Thread.sleep((long) (Math.random() * 3000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while (true) {
			String pedido = this.cola.articuloAleatorio();
			this.esperar(pedido);
			this.cola.hacerPedido(pedido);
			this.consumir(pedido);
		}
	}
}
