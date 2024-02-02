package psp.encriptacion.datos.mpq;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import encriptacion.Cifrado;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		byte opcion = -1;
		String textoCifrado;
		Cifrado cifrado = new Cifrado();
		String key;

		// Bucle que muestra las opciones del programa.
		while (opcion != 0) {
			Funciones.menu();
			opcion = sc.nextByte();
			sc.nextLine(); // Consumir el salto de línea después de la opción
			System.out.println();//Salto de linea.
			switch ((byte) opcion) {
			
			//Primer caso
			case 1:
				byte opcionCifrar = 0;
				try {
					System.out.println("Introduce el texto a descifrar: (Introduce la cadena de texto de la opcion 2)");
					String texto = sc.nextLine();

					System.out.println(cifrado.desencriptar(texto)); // Muestra la cadena de texto desencriptada.

				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
						| NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
					e.printStackTrace();
				}
				
				break;
				
				//Segundo caso
			case 2:
				System.out.println("Escribe el texto a cifrar.");
				textoCifrado = sc.nextLine();
				try {
					System.out.println("Texto cifrado: ");
					System.out.println(cifrado.encriptar(textoCifrado)); // Muestra la cadena de texto encriptada.
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
						| NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
					e.printStackTrace();
				}
				
				break;
			}
		}
	}

}
