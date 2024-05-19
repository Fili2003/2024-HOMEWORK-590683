package uniRoma3;

import static org.junit.Assert.*;

import org.junit.Test;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.comandi.Comando;
import it.uniroma3.diadia.comandi.FabbricaDiComandiFisarmonica;

import org.junit.*;
public class FabbricaDiComandiTest {

	private  FabbricaDiComandiFisarmonica fabbrica;
	private IO interfaccia;
	
	@Before
	public void setUp() {
		this.fabbrica = new FabbricaDiComandiFisarmonica(); 
	}
	
	@Test
	public void testCostruisciComandoVai() {
		
		Comando C = this.fabbrica.costruisciComando("vai nord",interfaccia);
		assertEquals("vai",C.getNome());
		assertEquals("nord",C.getParametro());
	}
	@Test
	public void testCostruisciComandoSbagliato() {
		
		Comando C = this.fabbrica.costruisciComando("vai nord",interfaccia);
		assertNotEquals("posa",C.getNome());
		assertNotEquals("pippo",C.getParametro());
	}
	
	@Test
	public void testCostruisciComandoNonValido() {
		
		Comando C = this.fabbrica.costruisciComando("vuoto",interfaccia);
		assertNull(C.getNome());
		assertNull(C.getParametro());
	}
	
}
