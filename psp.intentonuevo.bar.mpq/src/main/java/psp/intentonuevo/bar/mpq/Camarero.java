package psp.intentonuevo.bar.mpq;

public class Camarero extends Thread{
	private boolean estaAtendiendo;
	private Libreta libreta;
	
	
	public Camarero(boolean estaAtendiendo, Libreta libreta) {
		super();
		this.estaAtendiendo = estaAtendiendo;
		this.libreta = libreta;
	}
	
	
	
}
