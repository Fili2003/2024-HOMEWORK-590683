package uniRoma3;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.LabirintoBuilder;
import it.uniroma3.diadia.ambienti.labirinto;

public class PartitaTest {
	private Partita partita;
	private LabirintoBuilder lab;
	private labirinto labirinto;

	@Before
	public void setUp() {
		this.lab = new LabirintoBuilder().addStanzaIniziale("Atrio").addStanzaVincente("Biblioteca")
				.addAdiacenza("Atrio", "Biblioteca", "nord").addAttrezzo("osso", 1).getLabirinto();
		this.partita = new Partita(labirinto);
	}

	@Test // esiste una stanza corrente che settiamo
	public void testGetStanzaCorrenteCheEsiste() {
		assertEquals("Atrio", partita.getStanzaCorrente().getNome());
	}

	@Test // esiste una stanzxa corrente che è sbagliata
	public void testGetStanzaCorrenteCheNonGiusta() {
		assertNotEquals("biblioteca", partita.getStanzaCorrente().getNome());
	}

	// fare test per un altro emtodo in paritita vistoc he ho tolto CFU

	@Test // paritita vinta qaundo CFU 20
	public void testIsFinitaPerCfu() {
		assertEquals(20, partita.getGiocatore().getCfu());
	}

	@Test // partita finita quando arriva al labirinto
	public void testIfFinitaPercheVinto() {
		assertEquals("Biblioteca", lab.getStanzaVincente().getNome());
	}

	@Test // partita finita perche digita fine
	public void testIsFinitaPercheFine() {
		partita.setFinita();
		partita.isFinita();
		assertTrue(partita.isFinita());
	}
	@Test
	public void nonVinta() {
		assertFalse(partita.vinta());
	}
}
