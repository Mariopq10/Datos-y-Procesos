package prueba.hilos.mpq;

public class Main {

	public static void main(String[] args) {
		Raton tinky = new Raton("Tinky", 3);
		Raton winky = new Raton("Winky", 6);
		Raton lala = new Raton("Lala", 2);
		Raton poo = new Raton("Poo", 5);

		tinky.start();
		winky.start();
		poo.start();
		lala.start();

	}

}
