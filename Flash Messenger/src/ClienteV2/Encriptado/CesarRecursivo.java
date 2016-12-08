package ClienteV2.Encriptado;

public class CesarRecursivo {

	public static void main(String[] args) {
		
	String texto1 = "hola";
	String texto2 = "HoLa";
	String texto3 = "Aitor Ugarte Gomez";
	
	String cifrado1 = recorrer(1, "", texto1, 0);
	String cifrado2 = recorrer(1, "", texto2, 0);
	String cifrado3 = recorrer(1, "", texto3, 0);
	
	System.out.println(texto1 + " => " + recorrer(1, "", texto1, 0) + " => " + recorrer(2, cifrado1, "", 0));
	System.out.println(texto2 + " => " + recorrer(1, "", texto2, 0) + " => " + recorrer(2, cifrado2, "", 0));
	System.out.println(texto3 + " => " + recorrer(1, "", texto3, 0) + " => " + recorrer(2, cifrado3, "", 0));

	
	}

	/**
	 * Método que cifra cada letra de un String
	 * @param opcion 1 cifrar, 2 descifrar 
	 * @param cifrada palabra cifrada
	 * @param descifrada palabra en texto plano
	 * @param posicionActual posición respecto del String que va cifrando
	 * @return cifrada devuelve un texto cifrado
	 * @return descifrada devuelve un texto plano
	 */
	public static String recorrer(int opcion, String cifrada, String descifrada, int posicionActual) {

		if (opcion == 1) {
			int longitud = descifrada.length();

			if (posicionActual + 1 > longitud) {
				return cifrada;
			} else {
				char letra = descifrada.charAt(posicionActual);
				cifrada = cifrada + cifrarCesar(letra, 1);
				return recorrer(1, cifrada, descifrada, posicionActual + 1);
			}
		} else if (opcion == 2) {
			int longitud = cifrada.length();

			if (posicionActual + 1 > longitud) {
				return descifrada;
			} else {
				char letra = cifrada.charAt(posicionActual);
				descifrada = descifrada + descifrarCesar(letra, 1);
				return recorrer(2, cifrada, descifrada, posicionActual + 1);
			}
		} else {
			return null;
		}

	}

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
	public static char cifrarCesar(char letra, int codigo){
	
			//Busca si es minúscula
			for (int i = 0; i < minus.length; i++) {
				if (letra == minus[i]) {
					int posi = (i + codigo) % 27;
					letra = minus[posi];
					break;
				}
			
			}
			//Busca si es mayúscula
			for (int j = 0; j < mayus.length; j++) {
				if (letra == mayus[j]) {
					int posi = (j + codigo) % 27;
					letra = mayus[posi];
					break;
				}
			}
			
		return letra;
		
	}
	/*
	 * Descifrado César E(x) = x - n mod(27)
	 * La posición de la letra en el abcdario - el código % 27
	 */
	public static char descifrarCesar(char letra, int codigo){
		
		
		//Busca si es minúscula
		for (int i = 0; i < minus.length; i++) {
			if (letra == minus[i]) {
				if(codigo > i){
					if(codigo%27 ==0){
					codigo = codigo / 27;
					}else{
					codigo = minus.length - i - codigo;
					}
				}
				int posi = Math.abs((i - codigo) % 27);
				letra = minus[posi];
				break;
			}
		}
		
		//Busca si es mayúscula
		for (int j = 0; j < mayus.length; j++) {
			if (letra == mayus[j]) {
				if(codigo > j){
					if(codigo%27 ==0){
					codigo = codigo / 27;
					}else{
					codigo = minus.length - j - codigo;
					}
				}
				int posi = Math.abs((j - codigo) % 27);
				letra = mayus[posi];
				break;
			}		
	}
	return letra;
	}
}

