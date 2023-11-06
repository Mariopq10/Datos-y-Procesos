package psp.transporte.mpq;

public class Main {

	public static void main(String[] args) {
		Transporte coche = new Transporte("Coche", 30, 15);
		Transporte moto = new Transporte("Moto", 120, 8);
		Transporte patinete = new Transporte("Patinete", 10, 3);

		Thread t1 = new Thread(coche);
		Thread t2 = new Thread(moto);
		Thread t3 = new Thread(patinete);
		System.out.println("Comienza la ruta \n");
		t1.start();
		t2.start();
		t3.start();

	}

}
