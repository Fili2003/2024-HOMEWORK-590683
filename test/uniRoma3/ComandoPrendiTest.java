package uniRoma3;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.comandi.ComandoPrendi;

public class ComandoPrendiTest {

	private ComandoPrendi comando;
	private Partita partita;
	private IO io;

	@Before
	public void setUp() {
		this.io = new IOConsole();
		this.partita = new Partita();
		this.comando = new ComandoPrendi(io, "prendi");

	}

	@Test
	public void testEseguiPrendiCeLhai() {
		this.comando.setParametro("osso");
		this.comando.esegui(partita);
		assertEquals("osso", partita.getGiocatore().getBorsa().getAttrezzo("osso").getNome());
	}

	@Test
	public void testEseguiPrendiNonCeLhai() {
		this.comando.setParametro("osso");
		this.comando.esegui(partita);
		assertNotEquals("coltello", partita.getGiocatore().getBorsa().getAttrezzo("osso").getNome());
	}

	@Test
	public void testEseguiPrendoAttrezzoAssente() {
		this.comando = new ComandoPrendi(io, "pippo");
		this.comando.esegui(partita);
		assertNull(partita.getGiocatore().getBorsa().getAttrezzo("osso"));
	}
}
