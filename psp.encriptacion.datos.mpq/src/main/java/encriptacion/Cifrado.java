package encriptacion;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Ejemplo de encriptado y desencriptado con algoritmo AES.
 * Metodo Simétrico, misma clave entrada/salida.
 * 
 * @author Mario
 *
 */

public class Cifrado {

	/**
	 * Crea la clave de encriptacion usada internamente
	 * 
	 * @param clave Clave que se usara para encriptar
	 * @return Clave de encriptacion
	 **/

	private String claveSecreta;

	public Cifrado() {
		claveSecreta = "CifradoMario";
	}
	/**
	 * Funcion que crea una clave de tipo SecretKeySpec mediante el metodo de encriptacion AES.
	 * 
	 * @param clave Es la clave secreta que se le pasa por parametros.
	 * @return Devuelve la clave que crea usando el metodo de encriptacion AES
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	private SecretKeySpec crearClave(String clave) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] claveEncriptacion = clave.getBytes("UTF-8");
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		claveEncriptacion = sha.digest(claveEncriptacion);
		claveEncriptacion = Arrays.copyOf(claveEncriptacion, 16);

		SecretKeySpec secretKey = new SecretKeySpec(claveEncriptacion, "AES");

		return secretKey;
	}
/**
 * Funcion encriptar() que recibe una cadena de string y mediante la secretKey que recoge de la variable interna "claveSecreta" devuelve una cadena
 * de String encriptada.
 * 
 * @param datos Es una cadena de String que se le pasa por argumentos, que se encriptará posteriormente.
 * @return Devuelve una cadena de String encriptada.
 * @throws UnsupportedEncodingException
 * @throws NoSuchAlgorithmException
 * @throws InvalidKeyException
 * @throws NoSuchPaddingException
 * @throws IllegalBlockSizeException
 * @throws BadPaddingException
 */
	public String encriptar(String datos) throws UnsupportedEncodingException, NoSuchAlgorithmException,
			InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secretKey = this.crearClave(claveSecreta);

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		byte[] datosEncriptar = datos.getBytes("UTF-8");
		byte[] bytesEncriptados = cipher.doFinal(datosEncriptar);
		String encriptado = Base64.getEncoder().encodeToString(bytesEncriptados);

		return encriptado;
	}

	/**
	 * Funcion que recibe por parametros una cadena de String y la desencripta.
	 * @param datosEncriptados Cadena de String encriptada para su posterior desencriptamiento.
	 * @return Devuelve la cadena datosEncriptados desencriptandola usando la claveSecreta.
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public String desencriptar(String datosEncriptados) throws UnsupportedEncodingException, NoSuchAlgorithmException,
			InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secretKey = this.crearClave(claveSecreta);

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);

		byte[] bytesEncriptados = Base64.getDecoder().decode(datosEncriptados);
		byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados);
		String datos = new String(datosDesencriptados);

		return datos;
	}
}
