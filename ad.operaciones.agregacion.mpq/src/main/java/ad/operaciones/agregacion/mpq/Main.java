package ad.operaciones.agregacion.mpq;

import java.io.IOException;
import java.util.Arrays;

import org.bson.Document;



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
