package uniRoma3;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.ambienti.labirinto;

public class PartitaTest {
	private Partita partita;
	private labirinto labirinto;
	
	@Before
	public void setUp() {
		partita = new Partita();
		labirinto = new labirinto();
		Stanza s1= new Stanza("atrio");
		partita.setStanzaCorrente(s1);	}
	
	@Test //esiste una stanza corrente che settiamo
	public void testGetStanzaCorrenteCheEsiste() {
		assertEquals("atrio",partita.getStanzaCorrente().getNome());
	}
	@Test //esiste una stanzxa corrente che è sbagliata
	public void testGetStanzaCorrenteCheNonGiusta() {
		assertNotEquals("biblioteca",partita.getStanzaCorrente().getNome());
	}
	@Test //stanza vincente giusta
	public void testGetStanzaVincente() {
		assertEquals("Biblioteca",labirinto.getStanzaVincente().getNome());
		
		
	}

	// fare test per un altro emtodo in paritita vistoc he ho tolto CFU
	
	@Test //paritita vinta qaundo CFU 20
	public void testIsFinitaPerCfu() {
		assertEquals(20,partita.getGiocatore().getCfu());
	}
	@Test // partita finita quando arriva al labirinto
	public void testIfFinitaPercheVinto() {
		assertEquals("Biblioteca",labirinto.getStanzaVincente().getNome());
	}
	@Test //partita finita perche digita fine
	public void testIsFinitaPercheFine() {
		partita.setFinita();
		partita.isFinita();
		assertTrue(partita.isFinita());
	}
}









