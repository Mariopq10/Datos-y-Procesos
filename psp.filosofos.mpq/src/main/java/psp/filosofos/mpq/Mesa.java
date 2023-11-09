package psp.filosofos.mpq;

public class Mesa {
	private boolean[] palillos;

	public Mesa(byte i) {
		this.palillos = new boolean[i];
	}

	public int palilloDerecho(byte posicionFilosofo) {
		return posicionFilosofo;
	}

	public int palilloIzquierdo(byte posicionFilosofo) {
		if (posicionFilosofo == 0) {
			return this.palillos.length - 1;
		} else {
			return posicionFilosofo - 1;
		}
	}

	// Funcion que comprueba si los palillos izquierdo y derecho estan disponibles,
	// si lo estan, pasan a no estar disponibles.
	
	public synchronized void cogerPalillo(byte posicionFilosofo) {
		while (palillos[palilloDerecho(posicionFilosofo)] || palillos[palilloIzquierdo(posicionFilosofo)]) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		palillos[palilloDerecho(posicionFilosofo)] = true;
		palillos[palilloIzquierdo(posicionFilosofo)] = true;
	}

	public synchronized void soltarPalillo(byte posicionFilosofo) {
		palillos[palilloDerecho(posicionFilosofo)] = false;
		palillos[palilloIzquierdo(posicionFilosofo)] = false;
		notifyAll();
	}

}
