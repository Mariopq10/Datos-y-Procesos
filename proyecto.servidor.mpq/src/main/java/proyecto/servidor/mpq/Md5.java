package proyecto.servidor.mpq;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
	
    public static String cifrarConMD5(String contrasena) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(contrasena.getBytes());
            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
