package psp.productor.consumidor.mpq;

import java.util.ArrayList;

public class Buffer {
	private ArrayList <String> lista;

	public Buffer(ArrayList<String> lista) {
		super();
		this.lista = lista;
	}

	public ArrayList<String> getLista() {
		return lista;
	}

	public void setLista(ArrayList<String> lista) {
		this.lista = lista;
	}


	public void obtenerLista( ) {
        for (int i = 0; i<this.lista.size();i++) {
            System.out.print(this.lista.get(i)+" ");
        }
        System.out.println("\nEsta es la lista de productos.\n------------------------");
    }
	
	
}
