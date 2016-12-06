package ClienteV2.Encriptado;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class TiposCifrado {

	private static char[] minus = {'a','b','c','d','e','f','g','h','i','j',
									'k','l','m','n','ñ','o','p','q','r',
									's','t','u','v','w','x','y','z'};
	
    private static char[] mayus = {'A','B','C','D','E','F','G','H','I','J',
    								'K','L','M','N','Ñ','O','P','Q','R',
    								'S','T','U','V','W','X','Y','Z'};
    
	/*
	 * Cifrado César E(x) = x + n mod(27)
	 * La posición de la letra en el abcdario + el código % 27
	 */
	public static String cifrarCesar(String palabra, int codigo){

		char[] letras = palabra.toCharArray();

		for (int i = 0; i < letras.length; i++) {
			//Busca si es minúscula
			for (int j = 0; j < minus.length; j++) {
				if (letras[i] == minus[j]) {
					int posi = (j + codigo) % 27;
					letras[i] = minus[posi];
					break;
				}
			}
			
			//Busca si es mayúscula
			for (int j = 0; j < mayus.length; j++) {
				if (letras[i] == mayus[j]) {
					int posi = (j + codigo) % 27;
					letras[i] = mayus[posi];
					break;
				}
			}
			
		}
		return String.valueOf(letras);
		
	}
	/*
	 * Descifrado César E(x) = x - n mod(27)
	 * La posición de la letra en el abcdario - el código % 27
	 */
	public static String descifrarCesar(String palabra, int codigo){
		
		char[] letras = palabra.toCharArray();

		for (int i = 0; i < letras.length; i++) {
			
			//Busca si es minúscula
			for (int j = 0; j < minus.length; j++) {
				if (letras[i] == minus[j]) {
					int posi = (j - codigo) % 27;
					letras[i] = minus[posi];
					break;
				}
			}
			//Busca si es mayúscula
			for (int j = 0; j < mayus.length; j++) {
				if (letras[i] == mayus[j]) {
					int posi = (j - codigo) % 27;
					letras[i] = mayus[posi];
					break;
				}
			}
		}
		
		return String.valueOf(letras);
	}
	
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
