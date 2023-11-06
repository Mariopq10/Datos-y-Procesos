package psp.intentonuevo.bar.mpq;

public class Camarero implements Runnable{
	private boolean estaAtendiendo;
	private Libreta libreta;
	
	
	public Camarero(boolean estaAtendiendo, Libreta libreta) {
		super();
		this.estaAtendiendo = estaAtendiendo;
		this.libreta = libreta;
	}


	@Override
	public void run() {
		
		
	}
	
	
	
}
