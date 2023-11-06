package psp.intentonuevo.bar.mpq;

import java.util.ArrayList;

public class Libreta {
	private ArrayList<String> listado;

	public Libreta(ArrayList<String> listado) {
		super();
		this.listado = listado;
	}

	public ArrayList<String> getListado() {
		return listado;
	}

	public void setListado(ArrayList<String> listado) {
		this.listado = listado;
	}
	
	public synchronized void anotarLista() {
		if(getListado().size()!=0) {
			
		}
	}
	
	public synchronized void borrarDeLista() {
		
	}
}
