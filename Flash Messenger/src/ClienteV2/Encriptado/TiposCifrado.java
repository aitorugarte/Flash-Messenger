package ClienteV2.Encriptado;

public class TiposCifrado {

	private static char[] minus = {'a','b','c','d','e','f','g','h','i','j',
									'k','l','m','n','ñ','o','p','q','r',
									's','t','u','v','w','x','y','z'};
	
    private static char[] mayus = {'A','B','C','D','E','F','G','H','I','J',
    								'K','L','M','N','Ñ','O','P','Q','R',
    								'S','T','U','V','W','X','Y','Z'};
    
    
	public static void main(String[] args) {
		
		int codigo = 5;
		String palabra = cifrarCesar("HoLa QuE TaL", codigo );
		System.out.println("Palabra cifrada: " + palabra);
		System.out.println(descifrarCesar(palabra, codigo));
	}

	
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
	

}
