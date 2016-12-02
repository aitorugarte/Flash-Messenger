package ClienteV2.Encriptado;
 
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
 
/**
* Clase encargarda de encriptar texto
**/
public class AES {
 
   
    /**
     * Función de tipo String que recibe una llave (key), un vector de inicialización (vi)
     * y el texto que se desea cifrar
     * @param llave la llave en tipo String a utilizar
     * @param vi el vector de inicialización a utilizar
     * @param texto el texto sin cifrar a encriptar
     * @return el texto cifrado en modo String
     *@throws Exception puede devolver excepciones de muchos tipos     
     **/
    public static String encriptar(String llave, String vi, String texto) throws Exception {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec skeySpec = new SecretKeySpec(llave.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(vi.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(texto.getBytes());
            return new String(encodeBase64(encrypted));
    }
 
    
    
    
    /**
     * Función de tipo String que recibe una llave (key), un vector de inicialización (iv)
     * y el texto que se desea descifrar
     * @param llave la llave en tipo String a utilizar
     * @param vi el vector de inicialización a utilizar
     * @param encriptado el texto cifrado en modo String
     * @return el texto desencriptado en modo String
     * @throws Exception puede devolver excepciones de muchos tipos
     **/
    public static String decrypt(String llave, String vi, String encriptado) throws Exception {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec skeySpec = new SecretKeySpec(llave.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(vi.getBytes());
            byte[] enc = decodeBase64(encriptado);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
            byte[] decrypted = cipher.doFinal(enc);
            return new String(decrypted);
    }
 
}