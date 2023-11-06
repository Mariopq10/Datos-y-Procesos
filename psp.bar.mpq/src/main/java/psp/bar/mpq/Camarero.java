package psp.bar.mpq;

import java.util.ArrayList;

public class Camarero extends Thread {
	private boolean atendiendo;
	private ArrayList<Cliente> clientesAtendidos ;

	public Camarero(boolean atendiendo, ArrayList clientesAtendidos) {
		super();
		this.atendiendo = atendiendo;
		this.clientesAtendidos = clientesAtendidos;
	}

	public boolean isAtendiendo() {
		return atendiendo;
	}

	public void setAtendiendo(boolean atendiendo) {
		this.atendiendo = atendiendo;
	}

	public ArrayList<Cliente> getClientesAtendidos() {
		return clientesAtendidos;
	}

	public void setClientesAtendidos(ArrayList<Cliente> clientesAtendidos) {
		this.clientesAtendidos = clientesAtendidos;
	}

	public synchronized void atenderCliente(Cliente cliente) throws InterruptedException {
		System.out.println("Camarero atiende a "+cliente.getNombre());
		this.setAtendiendo(true);
		this.clientesAtendidos.add(cliente);
		Thread.sleep(2000);
		System.out.println("Camarero ha atendido a "+ cliente.getNombre());
		
	}

	@Override
	public void run() {
	
	}
}
