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

//		System.out.println("Escriba el texto a encriptar");
//		String texto = sc.nextLine();
//
//		try {
//			String textoEncriptado = cifrado.encriptar(texto, "mario");
//			System.out.println("La texto encriptado es:\n" + textoEncriptado);
//
//			String textoDesencriptado = cifrado.desencriptar(textoEncriptado, "mario");
//			System.out.println("La texto desencriptado es:\n" + textoDesencriptado);
//
//		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
//				| IllegalBlockSizeException | BadPaddingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		while (opcion != 0) {
			Funciones.menu();
			opcion = sc.nextByte();
			sc.nextLine(); // Consumir el salto de línea después de la opción

			System.out.println();
			switch ((byte) opcion) {

			case 1:

//				System.out.println("Escribe la clave");
//				key = sc.nextLine();
//				try {
//					cifrado.crearClave(key);
//					System.out.println("Clave cifrada correctamente");
//				} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
//					e.printStackTrace();
//				}

				break;

			case 2:
				byte opcionCifrar = 0;
				// System.out.println("Que archivo de cifrado quieres leer?\n-1)Texto
				// cifrado.\n.2)Seleccionar archivo.");
				// opcionCifrar = sc.nextByte();
				// sc.nextLine();
				try {
//					System.out.println("Introduce la clave de cifrado.");
//					key = sc.nextLine();
					System.out.println("Introduce el texto a descifrar: ");
					String texto = sc.nextLine();

					System.out.println(cifrado.desencriptar(texto));

				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
						| NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
					e.printStackTrace();
				}

				break;

			case 3:
				System.out.println("Escribe el texto a cifrar.");
				textoCifrado = sc.nextLine();
//				System.out.println("Introduce la clave de cifrado: ");
//				key = sc.nextLine();

				try {
					System.out.println("Texto cifrado: ");
					System.out.println(cifrado.encriptar(textoCifrado));
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
						| NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {

					e.printStackTrace();
				}

				break;
			}
		}

	}

}
