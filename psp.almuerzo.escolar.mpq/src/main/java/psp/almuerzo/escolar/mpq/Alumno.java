package psp.almuerzo.escolar.mpq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Alumno extends Thread {
	private String nombre;
	private ArrayList comidas=new ArrayList<>(
			Arrays.asList("Patata","Pasta","Chocolate"));
	private int tiempo;

	public Alumno(String nombre,int tiempo) {
		super();
		this.nombre = nombre;
		this.tiempo = tiempo;
	}
	
	public Alumno(String nombre) {
		super();
		this.nombre = nombre;
		this.comidas = comidas;
		Random random = new Random();
		int numeroAleatorio = random.nextInt(10);
		this.tiempo = numeroAleatorio;
	}



	public ArrayList getComidas() {
		return comidas;
	}

	public void setComidas(ArrayList comidas) {
		this.comidas = comidas;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
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
		int numeroAleatorio = random.nextInt(alumno.getComidas().size());
		articulo = (String) alumno.getComidas().get(numeroAleatorio);
		if (alumno.getComidas().contains(articulo)) {
 			alumno.getComidas().remove(articulo);
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

				Thread.sleep(this.tiempo*1500);
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
