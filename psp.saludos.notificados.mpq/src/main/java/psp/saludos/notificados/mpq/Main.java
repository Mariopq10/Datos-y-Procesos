package psp.saludos.notificados.mpq;

public class Main {

	public static void main(String[] args) {
		Saludo saludo1 = new Saludo("Hola Jefe");
		Saludo saludo2 = new Saludo("Hola Jefe");
		Saludo saludo3 = new Saludo("Hola Jefe");
		Saludo saludoJefe = new Saludo("Hola a todos");
		Personal jefe = new Personal("Jefe", true, saludoJefe);
		Personal empleado1 = new Personal("Empleado1", false, saludo1);
		Personal empleado2 = new Personal("Empleado2", false, saludo2);
		Personal empleado3 = new Personal("Empleado3", false, saludo3);
		
		
		empleado1.start();
		empleado2.start();
		empleado3.start();
		jefe.start();

	}

}
