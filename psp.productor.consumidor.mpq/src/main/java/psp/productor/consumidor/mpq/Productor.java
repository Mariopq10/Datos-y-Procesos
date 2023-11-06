package psp.productor.consumidor.mpq;

import java.util.ArrayList;
import java.util.Random;
import java.util.random.*;

public class Productor extends Thread {
	private String nombre;
	private String articulo;
	private Buffer buffer;

	public Productor(String nombre, Buffer buffer) {
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

	public synchronized void put() throws InterruptedException {
		
		buffer.getLista().add(articuloAleatorio());
		Thread.sleep(1000);
		notify();
	
	}

	@Override
	public void run() {
		try {
			while (buffer.getLista().size() < 7) {
				put();
				buffer.obtenerLista();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Productor [nombre=" + nombre + "]";
	}

}
