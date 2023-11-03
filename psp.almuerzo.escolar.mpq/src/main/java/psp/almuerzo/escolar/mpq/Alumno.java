package psp.almuerzo.escolar.mpq;

import java.util.Random;

public class Alumno extends Thread {
	private String nombre;
	private String[] comidas;

	public Alumno(String nombre) {
		super();
		this.nombre = nombre;
	}

	public String getComidas() {
		String lista = "";
		for (byte i = 0; i < comidas.length; i++) {
			lista += comidas[i];
		}
		return lista;
	}

	public void setComidas(String[] comidas) {
		this.comidas = comidas;
	}

	public Alumno(String nombre, String[] comidas) {
		super();
		this.nombre = nombre;
		this.comidas = comidas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String articuloAleatorio(Alumno alumno) {
		String[] lista = new String[] { "Patata", "Pasta", "Chocolate" };
		String articulo;
		Random random = new Random();
		int numeroAleatorio = random.nextInt(lista.length);
		articulo = lista[numeroAleatorio];
		if (alumno.getComidas().contains(articulo)) {
			this.articuloAleatorio(alumno);
		} else {
			return articulo;
		}
		return articulo;

	}

	@Override
	public void run() {
		byte iterador = 0;
		while (iterador != 3) {
			try {
				System.out.println(this.nombre + " empieza la merienda de " + articuloAleatorio(this));

				Thread.sleep(1500);
				System.out.println(this.nombre + " termino de comer");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			iterador++;
		}
	}

	@Override
	public String toString() {
		return "Alumno [nombre=" + nombre + "]";
	}

}
