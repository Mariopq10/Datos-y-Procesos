package psp.intentonuevo.bar.mpq;

public class Cliente implements Runnable{
	private String nombre;
	private String comanda;
	private Libreta libreta;
	
	public Cliente(String nombre, String comanda, Libreta libreta) {
		super();
		this.nombre = nombre;
		this.comanda = comanda;
		this.libreta = libreta;
	}

	@Override
	public void run() {
		
		
	}
	
	
	
}
