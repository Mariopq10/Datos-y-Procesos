package psp.productor.consumidor.mpq;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		ArrayList<String> lista = new ArrayList<String>();
		Buffer buffer = new Buffer(lista);
		Productor productor = new Productor("Productor", buffer);
		Consumidor consumidor = new Consumidor("Consumidor", buffer);
		System.out.println(System.getProperty("java.runtime.version"));
		
		productor.start();
		consumidor.start();

	}

}
