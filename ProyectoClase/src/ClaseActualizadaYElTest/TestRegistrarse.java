package ClaseActualizadaYElTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ClienteV2.LogIn.Registrarse;

public class TestRegistrarse {

	Registrarse prueba = new Registrarse();

	@Before
	public void setUp() throws Exception {
		prueba.setUno('c');
		prueba.setDos('c');

	}

	@Test
	public void testComprobarPass() throws Exception {
		assertEquals(false, prueba.comprobarPass());
	}

}
