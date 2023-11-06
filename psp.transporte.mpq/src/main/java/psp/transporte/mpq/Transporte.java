package psp.transporte.mpq;

public class Transporte implements Runnable {
	private String nombre;
	private float velocidad;
	private float kmTotales;
	private float distanciaRecorrida;
	


	public Transporte(String nombre, int velocidad, float distanciaARecorrer) {
		super();
		this.nombre = nombre;
		this.velocidad = velocidad;
		this.kmTotales =distanciaARecorrer;
	}


	public void run() {
		while(kmTotales>distanciaRecorrida) {
			distanciaRecorrida += 5*(this.velocidad/60/60);
			float distanciaRestante = (float) (kmTotales-distanciaRecorrida);
            System.out.println(nombre + " ha recorrido " + distanciaRecorrida + " km. Quedan " + distanciaRestante + " km por recorrer. A "+this.velocidad+" km/h");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}System.out.println(this.nombre + " terminó su ruta.");
	}

}
