package ad.operaciones.agregacion.mpq;

import java.io.IOException;

public class Main {

	
	public static void main(String[] args) {
		Conexion conexion = new Conexion();

		try {
			Funciones.displayMenu();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
