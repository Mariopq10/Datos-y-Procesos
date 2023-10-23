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

	public void get() {
		try {
			wait();
		} catch (Exception e) {
		}
		String art = articuloAleatorio();
		if (buffer.getLista().contains(art)) {
			buffer.getLista().remove(art);
			System.out.println("Se consumio un/una: " + art + "\n");
		} else {
			System.out.println("No hay " + art + " , no se ha consumido nada\n");
		}

	}

	@Override
	public void run() {
		try {
			while (buffer.getLista().size() > 0) {
				get();
				Thread.sleep(1500);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Consumidor [nombre=" + nombre + "]";
	}

}
