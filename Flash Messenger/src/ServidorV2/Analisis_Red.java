package ServidorV2;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ServidorV2.Ventana_Servidor;

public class Analisis_Red {

	private static int c = 100;

	public static ArrayList<Integer> almacen = new ArrayList<Integer>();


	public Analisis_Red(int puerto, int fin) {

		Socket prueba;
		String ip = "localhost";

		for (int port = puerto; port <= fin; port++) {

			try {
				prueba = new Socket(ip, port);

				System.out.println("Puerto " + port + " Abierto");
				almacen.add(port);
				prueba.close();

			} catch (Exception ex) {

			}

			Ventana_Servidor.progressBar.setValue(c);
			c = c + 1;
		}
	}

	/**
	 * Método que indica si hay disponible una conexión a internet
	 * 
	 * @return true si hay internet
	 * @return false si no hay internet
	 */
	public static boolean TestInternet() {

		String web = "www.google.es";
		int puerto = 80;
		boolean hayinternet = false;
		Socket test = null;
		try {
			test = new Socket(web, puerto);
			if (test.isConnected()) {
				// Si hay internet comprobamos el puerto 3306
				if (TestPuerto() == true) {
					hayinternet = true;
				}
			}
		} catch (Exception e) {
			return false;
		}
		// Cerramos el puerto
		try {
			test.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hayinternet;
	}

	/*
	 * Método que comprueba si el puerto necesario para conectarse a la bd está
	 * abierto o cerrado
	 * 
	 * @return true si está abierto
	 * 
	 * @return false si está cerrado
	 */
	private static boolean TestPuerto() {
		boolean abierto = true;
		try {
			Socket s = null;
			try {
				// Comprobamos con nuestro host
				s = new Socket("www.phpmyadmin.co", 3306);
			} catch (ConnectException e) {
				abierto = false;
			}
			// Cerramos el puerto
			s.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return abierto;
	}
}

class ejecutarTareas implements Runnable {
	int inicio = 0;
	int fin;

	public ejecutarTareas(int inicio, int fin) {
		this.inicio = inicio;
		this.fin = fin;
	}

	@Override
	public void run() {
		Analisis_Red scan = new Analisis_Red(inicio, fin);
	}

}
