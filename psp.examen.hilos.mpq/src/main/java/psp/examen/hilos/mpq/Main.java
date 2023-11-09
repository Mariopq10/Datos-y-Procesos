package psp.examen.hilos.mpq;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		ArrayList listaPedidos = new ArrayList<String>();
		
		Cola cola = new Cola(listaPedidos);
		
		Productor productor = new Productor(cola, "Productor");
		Consumidor consumidor1 = new Consumidor("Cliente1", cola);
		Consumidor consumidor2 = new Consumidor("Cliente2", cola);
		
		productor.start();
		consumidor1.start();
		consumidor2.start();
		

	}

}
