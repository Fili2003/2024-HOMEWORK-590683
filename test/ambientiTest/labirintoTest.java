package ambientiTest;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;

import it.uniroma3.diadia.ambienti.labirinto;

public class labirintoTest {

	private labirinto localLabirinto; // cambio tutto quello che si chiama uguale refactor->rename

	@Before
	public void setUp() {
		this.localLabirinto = new labirinto();
	}

//TEST PER GETSTANZAVINCENTE
	// upperCase trasforma tutto in caratteri maiuscoli cosi on ho problemi nel
	// capire
	@Test
	public void getStanzaVincenteGiusta() {
		assertEquals(labirinto.bilbioteca.toUpperCase(), localLabirinto.getStanzaVincente().getNome().toUpperCase());
	}

	@Test
	public void getStanzaVincenteNonGiusta() {
		assertNotEquals("pippo", localLabirinto.getStanzaVincente().getNome());
	}

	@Test
	public void getStanzaVincenteNonNull() {
		assertNotNull(localLabirinto.getStanzaVincente());
	}

// STESSI TEST PER GETSTANZAINIZIALE
	@Test
	public void getStanzaInizialeGiusta() {
		assertEquals("Atrio", localLabirinto.getStanzaIniziale().getNome());

	}

	@Test
	public void getStanzaInizialeGiustaNonGiusta() {
		assertNotEquals("namoooo", localLabirinto.getStanzaIniziale().getNome());
	}

	@Test
	public void getStanzaInizialeGiustaNonNull() {
		assertNotNull(localLabirinto.getStanzaIniziale());

	}
}
