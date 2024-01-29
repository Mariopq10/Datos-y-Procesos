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

	private SecretKeySpec crearClave(String clave) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] claveEncriptacion = clave.getBytes("UTF-8");
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		claveEncriptacion = sha.digest(claveEncriptacion);
		claveEncriptacion = Arrays.copyOf(claveEncriptacion, 16);

		SecretKeySpec secretKey = new SecretKeySpec(claveEncriptacion, "AES");

		return secretKey;
	}

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