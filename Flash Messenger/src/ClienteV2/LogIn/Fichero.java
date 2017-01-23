package ClienteV2.LogIn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

/**
 * Clase cedida por David García
 * Guarda y carga la lista de usuarios conectados
 */
public class Fichero {


	public static String RutaAbsoluta = "C:/Users/Usuario/Desktop/Flash Messenger/resources/";

	public void guardar(Object obj, String archivo) {
		try {
			File f = new File(RutaAbsoluta + archivo);
			FileOutputStream fout = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(obj); // Donde escribimos el dato.
			oos.close();
		} catch (IOException e) {
		}
	}

	public LinkedList<String> cargar(String archivo) {

		LinkedList<String> devLista = new LinkedList<String>();

		try {
			FileInputStream fin = new FileInputStream(RutaAbsoluta + archivo);
			ObjectInputStream ois = new ObjectInputStream(fin);
			@SuppressWarnings("unchecked")
			LinkedList<String> lista = (LinkedList<String>) ois.readObject();
			int c = 0;
			do {
				devLista.add(lista.get(c));
				c++;
			} while (c < lista.size());
			ois.close();
		} catch (Exception e) {
		}

		return devLista;

	}

}
