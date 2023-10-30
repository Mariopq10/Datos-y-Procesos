package psp.saludos.notificados.mpq;

public class Main {

	public static void main(String[] args) {
		Saludo saludo1 = new Saludo("");
		Personal jefe = new Personal("Jefe", true, saludo1);
		Personal empleado1 = new Personal("Empleado1", false, saludo1);
		Personal empleado2 = new Personal("Empleado2", false, saludo1);
		Personal empleado3 = new Personal("Empleado3", false, saludo1);
		
		
		empleado1.start();
		empleado2.start();
		empleado3.start();
		jefe.start();

	}

}
