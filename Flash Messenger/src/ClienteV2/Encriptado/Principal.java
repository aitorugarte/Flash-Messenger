package ClienteV2.Encriptado;

public class Principal {

	public static void main(String[] args) throws Exception {

		String llave = "92AE31A79FEEB2A3"; // llave
		String vi = "0123456789ABCDEF"; // vector de inicialización
		String texto = "hola";
		
		System.out.println("Texto normal " + texto);
		System.out.println("Texto encriptado: " + AES.encriptar(llave, vi, texto));
		System.out.println("Texto desencriptado: "
				+ AES.decrypt(llave, vi, AES.encriptar(llave, vi, texto)));
	}

}
