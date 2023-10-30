package psp.saludos.notificados.mpq;

public class Personal extends Thread {
	private String nombre;
	private boolean jefe;
	private Saludo saludoObj;

	public Personal(String nombre, boolean jefe, Saludo saludoObj) {
		super();
		this.nombre = nombre;
		this.jefe = jefe;
		this.saludoObj = saludoObj;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isJefe() {
		return jefe;
	}

	public void setJefe(boolean jefe) {
		this.jefe = jefe;
	}

	@Override
	public void run() {
		System.out.println(this.nombre + " ha llegado");

		if (jefe) {
			this.saludoObj.saludoJefe();
		} else if (jefe == false) {
			this.saludoObj.saludoEmpleado();

		}
	}
	@Override
	public String toString() {
		return "Personal [nombre=" + nombre + ", jefe=" + jefe + "]";
	}

}
