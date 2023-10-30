package psp.saludos.notificados.mpq;

public class Saludo {
	private String saludo;

	public Saludo(String saludo) {
		super();
		this.saludo = saludo;
	}

	public synchronized void saludoEmpleado() {

		try {
			System.out.println("\tEmpleado esperando...");
			wait();
			System.out.println("Hola jefe");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		notify();

	}

	public synchronized void saludoJefe() {

		System.out.println("El jefe dice hola a todos");

	}

}
