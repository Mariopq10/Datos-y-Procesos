package psp.bar.mpq;

public class Cliente extends Thread {
	private String nombre;
	private String comanda;
	private Camarero camarero;
	private boolean atendido;

	public Cliente(String nombre, boolean atendido, Camarero camarero) {
		super();
		this.nombre = nombre;
		this.comanda = "Cerveza";
		this.atendido = atendido;
		this.camarero = camarero;
	}

	public String getNombre() {
		return nombre;
	}

	public Camarero getCamarero() {
		return camarero;
	}

	public void setCamarero(Camarero camarero) {
		this.camarero = camarero;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getComanda() {
		return comanda;
	}

	public void setComanda(String comanda) {
		this.comanda = comanda;
	}

	public boolean isAtendido() {
		return atendido;
	}

	public void setAtendido(boolean atendido) {
		this.atendido = atendido;
	}

	public synchronized void pedirComanda(Camarero camarero) throws InterruptedException {
		if (camarero.isAtendiendo()) {
			System.out.println("Camarero ocupado");
			wait();
		} else {

			camarero.atenderCliente(this);
			System.out.println(this.nombre + " pide al camarero: " + this.comanda);

			this.camarero.setAtendiendo(false);

		}

	}

	@Override
	public void run() {
		try {
			System.out.println(this.nombre + " llama al camarero esperando a que lo atienda");
			this.pedirComanda(this.camarero);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
