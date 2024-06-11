package comandiTest;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.labirinto;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.ComandoPosa;

import org.junit.*;

public class ComandoPosaTest {

	private Partita partita;
	private ComandoPosa posa;
	private Attrezzo a;
	private IO io;
	private labirinto labirinto;
	private Scanner scanner;

	@Before
	public void setUp() {
		this.io = new IOConsole(scanner);
		this.partita = new Partita(labirinto);
		this.posa = new ComandoPosa("posa", io);

	}

	@Test
	public void testEseguiPosaAttrezzo() {
		this.a = new Attrezzo("lama", 10);
		this.partita.getGiocatore().getBorsa().addAttrezzo(a);
		this.posa.setParametro("lama");
		this.posa.esegui(partita);
		assertTrue(partita.getStanzaCorrente().hasAttrezzo("lama"));
	}

	@Test
	public void testEseguiPosaAttrezzoSbagliato() {
		this.a = new Attrezzo("lama", 10);
		this.partita.getGiocatore().getBorsa().addAttrezzo(a);
		this.posa.setParametro("lama");
		this.posa.esegui(partita);
		assertFalse(partita.getStanzaCorrente().hasAttrezzo("altro"));
	}

	@Test
	public void testEseguiPosaAttrezzoNullo() {
		this.a = new Attrezzo("lama", 10);
		this.partita.getGiocatore().getBorsa().addAttrezzo(a);
		this.posa.setParametro("altro");
		this.posa.esegui(partita);
		assertNull(partita.getStanzaCorrente().getAttrezzo("altro"));
	}

}
