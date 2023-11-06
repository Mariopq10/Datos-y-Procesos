package psp.bar.mpq;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		ArrayList<Cliente> listado = new ArrayList<Cliente>();
		Camarero camarero = new Camarero(false, listado);
		camarero.start();
		Cliente mario = new Cliente("Mario", false, camarero);
		Cliente porras = new Cliente("Porras", false, camarero);
		Cliente alemarica = new Cliente("Ale", false, camarero);

		mario.start();
		porras.start();
		alemarica.start();
		
		

	}

}
