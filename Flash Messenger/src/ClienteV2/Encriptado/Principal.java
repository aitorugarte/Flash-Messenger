package ClienteV2.Encriptado;

public class Principal {

	public static void main(String[] args) throws Exception {
		
		int codigo = 5; //codigo
		String llave = "92AE31A79FEEB2A3"; // llave
		String vi = "0123456789ABCDEF"; // vector de inicialización
		String texto = "hola"; //Texto a cifrar
		
		System.out.println("   .:Cifrado César:. "
				+ "\nTexto normal: " + texto
				+ "\nTexto cifrado: " + TiposCifrado.cifrarCesar(texto, codigo));
		System.out.println();
		System.out.println("   .:Cifrado AES:."
				+ "\nTexto normal: " + texto
				+ "\nTexto cifrado: " + TiposCifrado.encriptar(llave, vi, texto));
		
	}

}
