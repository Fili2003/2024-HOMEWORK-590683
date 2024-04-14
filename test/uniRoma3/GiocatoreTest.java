package uniRoma3;

import static org.junit.Assert.*;

import org.junit.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.giocatore.Borsa;
import it.uniroma3.diadia.giocatore.Giocatore;

import org.junit.*;

public class GiocatoreTest {

	private Giocatore giocatore;
	private Borsa borsa;
	private Attrezzo attrezzoBorsa;
	
	@Before
	public void setUp() {
		giocatore = new Giocatore();
		borsa = new Borsa();
		attrezzoBorsa = new Attrezzo("attrezzo", 1);
		
		
	}
	
	// TEST PER getBorsa
	
	@Test // test borsa vuota
	public void getBorsaVuota() {
		assertTrue(giocatore.getBorsa().isEmpty());
	}
	@Test // test borsa non vuota
	public void getBorsaNonVuota() {
		giocatore.getBorsa().addAttrezzo(attrezzoBorsa);
		assertFalse(giocatore.getBorsa().isEmpty());
	}
	@Test // test borsa con un determinato attrezzo 
	public void getBorsaConDeterminatoAttrezzo() {
		borsa.addAttrezzo(attrezzoBorsa);
		assertEquals(attrezzoBorsa,borsa.getAttrezzo("attrezzo"));
	}
	
	//TEST PER getCfu
	
	@Test //partita appena iniziata ci sono giustamente 20 CFU
	public void testGetCfuMaxAppenaIniziata() {
		assertEquals(20,giocatore.getCfu());
	}
	@Test //partita senza CFU 
	public void testGetCfuPartitaFinitaSenzaCfu() {
		giocatore.setCfu(0);
		assertEquals(0,giocatore.getCfu());
	}
	@Test //partita apppena inziziata che prova se non ci sono 20 cfu 
	public void testGetCfuPartitaIniziataNo20Cfu() {
		assertNotEquals(26374837,giocatore.getCfu());
	}

}
