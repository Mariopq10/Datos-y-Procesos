package psp.examen.hilos.mpq;

import java.util.ArrayList;
import java.util.Random;

public class Cola {
	public ArrayList<String> lista;

	public Cola(ArrayList<String> lista) {
		super();
		this.lista = lista;
	}

	
	//Funcion que devuelve un articulo aleatorio entre cerveza cocacola y fanta.
	public String articuloAleatorio() {
		String[] lista = new String[] { "Cerveza", "Cocacola", "Fanta" };
		String articulo;
		Random random = new Random();
		int numeroAleatorio = random.nextInt(lista.length);
		articulo = lista[numeroAleatorio];
		return articulo;
	}
	
	//Funcion que devuelve la lista de productos que están en la cola de pedidos.
	public void obtenerLista() {
		System.out.println("\nEsta es la lista de productos restantes pedidos");
		for (int i = 0; i < this.lista.size(); i++) {
			System.out.print(this.lista.get(i) + " ");
		}
		System.out.println("\n\n<--------------------------------------------------------------------------------->");
	}
	
	//Funcion crearPedido que recibe un pedido que luego eliminará de la lista cuando la funcion hacerPedido le haga el notify.
	public synchronized void hacerPedido(String pedido) {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		lista.remove(pedido);
	}
	//Funcion hacerPedido que añade un articulo aleatorio a la lista.
	public synchronized void servirPedido() {
		lista.add(articuloAleatorio());
		this.obtenerLista();
		notifyAll();
	}

}
