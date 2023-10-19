package inconsistencia.hilos.mpq;

public class Hilos extends Thread {
	static int contador=0;
	String hilo;
	
	public Hilos(String hilo) {
		super();
		this.hilo = hilo;
	}

	@Override
	public void run() {
		super.run();
		int cuenta =contador;
		
		while(cuenta<20) {
			System.out.println(hilo+" = "+contador);
			cuenta++;
			contador=cuenta;
		}
	}
	
	
	
	
}
