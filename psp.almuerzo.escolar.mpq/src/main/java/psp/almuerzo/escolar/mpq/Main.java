package psp.almuerzo.escolar.mpq;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		ArrayList<String> lista = new ArrayList<String>(
				Arrays.asList("Mario", "Pepe", "Ricardo", "Pedro", "Tomas", "Miri", "Maria", "Carmen", "Paco", "Juan"));

		ArrayList<Alumno> listado = new ArrayList<Alumno>();
		String[] comidasHechas = { "" };
		byte iterador = 0;

		for (byte i = 0; i < lista.size(); i++) {
			Alumno alumno = new Alumno(lista.get(i));
			listado.add(alumno);
			//alumno.start();
		}
		for (byte i = 0; i < listado.size(); i++) {
			listado.get(i).start();
		}

	}

}
