package psp.filosofos.mpq;

public class Main {

	public static void main(String[] args) {
		Mesa mesa = new Mesa((byte) 5);

		for (byte i = 0; i < 5; i++) {
			Filosofo filosofo = new Filosofo(mesa, i);
			filosofo.start();
		}

	}

}
