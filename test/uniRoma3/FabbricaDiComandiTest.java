package uniRoma3;

import static org.junit.Assert.*;

import org.junit.Test;

import it.uniroma3.diadia.comandi.Comando;
import it.uniroma3.diadia.comandi.FabbricaDiComandiFisarmonica;

import org.junit.*;
public class FabbricaDiComandiTest {

	private  FabbricaDiComandiFisarmonica fabbrica;
	
	@Before
	public void setUp() {
		this.fabbrica = new FabbricaDiComandiFisarmonica(); 
	}
	
	@Test
	public void testCostruisciComandoVai() {
		
		Comando C = this.fabbrica.costruisciComando("vai nord");
		assertEquals("vai",C.getNome());
		assertEquals("nord",C.getParametro());
	}
	@Test
	public void testCostruisciComandoSbagliato() {
		
		Comando C = this.fabbrica.costruisciComando("vai nord");
		assertNotEquals("posa",C.getNome());
		assertNotEquals("pippo",C.getParametro());
	}
	
	@Test
	public void testCostruisciComandoNonValido() {
		
		Comando C = this.fabbrica.costruisciComando("vuoto");
		assertNull(C.getNome());
		assertNull(C.getParametro());
	}
	
}
