package psp.productor.consumidor.mpq;

import java.util.Random;

public class Consumidor extends Thread {
	private String nombre;
	private Buffer buffer;

	public Consumidor(String nombre, Buffer buffer) {
		super();
		this.nombre = nombre;
		this.buffer = buffer;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String articuloAleatorio() {
		String[] lista = new String[] { "pizza", "cocacola", "fanta" };
		String articulo;
		Random random = new Random();
		int numeroAleatorio = random.nextInt(lista.length);
		articulo = lista[numeroAleatorio];
		return articulo;
	}

	public synchronized void get() throws InterruptedException {

		String art = articuloAleatorio();
		if (buffer.getLista().contains(art)) {
			buffer.getLista().remove(art);
			System.out.println("Se consumio un/una: " + art);
		} else {
			System.out.println("No hay " + art + " , no se ha consumido nada");
		}
		Thread.sleep(500);
		
	}

	public synchronized boolean existeLista() throws InterruptedException {
		boolean ret=false;
		if (this.buffer.getLista().size() == 0) {
			
			ret=true;
		}
		return ret;
		
	}
	@Override
	public void run() {

		while (true) {
			try {
				Thread.sleep(1000);
				if (existeLista()) {}
				
				get();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public String toString() {
		return "Consumidor [nombre=" + nombre + "]";
	}

}
