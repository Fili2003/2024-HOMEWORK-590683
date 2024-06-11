package uniRoma3;

import static org.junit.Assert.*;

import org.junit.Test;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.comandi.FabbricaComandiRiflessiva;
import it.uniroma3.diadia.comandi.abstractComando;

import org.junit.*;
public class FabbricaDiComandiTest {

	private  FabbricaComandiRiflessiva fabbrica;
	private IO interfaccia;
	
	@Before
	public void setUp() {
		this.fabbrica = new FabbricaComandiRiflessiva(interfaccia); 
	}
	
	@Test
	public void testCostruisciComandoVai() {
		
		abstractComando C = this.fabbrica.costruisciComando("vai nord",interfaccia);
		assertEquals("vai",C.getNome());
		assertEquals("nord",C.getParametro());
	}
	@Test
	public void testCostruisciComandoSbagliato() {
		
		abstractComando C = this.fabbrica.costruisciComando("vai nord",interfaccia);
		assertNotEquals("posa",C.getNome());
		assertNotEquals("pippo",C.getParametro());
	}
	
	@Test
	public void testCostruisciComandoNonValido() {
		
		abstractComando C = this.fabbrica.costruisciComando("vuoto",interfaccia);
		assertNull(C.getNome());
		assertNull(C.getParametro());
	}
	
}
