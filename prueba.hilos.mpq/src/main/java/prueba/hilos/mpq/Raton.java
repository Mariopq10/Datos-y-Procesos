package prueba.hilos.mpq;

public class Raton extends Thread {
	private String nombre;
	private int tiempo;

	public Raton(String nombre, int tiempo) {
		super();
		this.nombre = nombre;
		this.tiempo = tiempo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

	@Override
	public void run() {
		try {
			System.out.println(this.nombre + " empieza la merienda");
			Thread.sleep(500 * this.tiempo);
			System.out.println(this.nombre + " termino de comer");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Raton [nombre=" + nombre + ", tiempo=" + tiempo + "]";
	}

}
