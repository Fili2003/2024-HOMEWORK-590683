package uniRoma3;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.LabirintoBuilder;
import it.uniroma3.diadia.ambienti.StanzaBuia;
import it.uniroma3.diadia.comandi.Comando;
import it.uniroma3.diadia.comandi.FabbricaDiComandiFisarmonica;
import it.uniroma3.diadia.giocatore.Giocatore;

public class partiteIntereTest {

	private Partita partita;
	private LabirintoBuilder labirinto;
	private Giocatore giocatore;
	private Comando comando;
	private IO io;
	private FabbricaDiComandiFisarmonica factory;

	@Before
	public void setUp() {
		this.labirinto = new LabirintoBuilder().addStanzaIniziale("ingresso").addAttrezzo("spada", 1)
				.addStanzaVincente("uscita").addStanza("salone").addAttrezzo("cucchiaio", 1)
				.addAdiacenza("ingresso", "salone", "nord").addAdiacenza("salone", "ingresso", "sud").addStanza("bagno")
				.addAttrezzo("torcia", 1).addStanzaBuia("cameraDaLetto", "torcia").addAttrezzo("pass", 2)
				.addAdiacenza("salone", "bagno", "ovest").addAdiacenza("bagno", "salone", "est")
				.addAdiacenza("salone", "cameraDaLetto", "nord").addAdiacenza("cameraDaLetto", "salone", "sud")
				.addStanzaMagica("studio", 0).addAttrezzo("penna", 1).addAdiacenza("studio", "cameraDaLetto", "ovest")
				.addStanzaBloccata("bunker", "est", "pass").addAdiacenza("cameraDaLetto", "studio", "est")
				.addAdiacenza("ingresso", "bunker", "est").addAdiacenza("bunker", "ingresso", "ovest")
				.addAdiacenza("bunker", "uscita", "est").addAdiacenza("uscita", "bunker", "ovest").getLabirinto();
		this.partita = new Partita(labirinto);
		this.giocatore = partita.getGiocatore();
		this.io = new IOConsole();
		this.factory = new FabbricaDiComandiFisarmonica();
	}

	@Test
	public void testControlloParametriIniziali() {
		assertEquals(20, giocatore.getCfu());
		assertEquals(7, labirinto.getStanze().size());
		assertFalse(partita.isFinita());
		assertEquals(partita.getStanzaCorrente(), labirinto.getStanzaIniziale());
		assertTrue(partita.giocatoreIsVivo());
		assertEquals(labirinto.getStanze().get("ingresso"), labirinto.getStanzaIniziale());
		assertEquals(labirinto.getStanze().get("uscita"), labirinto.getStanzaVincente());
		assertTrue(partita.getGiocatore().getBorsa().isEmpty());
		assertTrue(partita.getStanzaCorrente().hasAttrezzo("spada"));
	}

	@Test
	public void testComandiPrendi() {
		this.comando = factory.costruisciComando("prendi spada", io);
		comando.esegui(partita);
		assertFalse(giocatore.getBorsa().isEmpty());
		assertTrue(giocatore.getBorsa().hasAttrezzo("spada"));
		assertFalse(partita.getStanzaCorrente().hasAttrezzo("spada"));

		this.comando = factory.costruisciComando("vai nord", io);
		comando.esegui(partita);
		assertEquals("salone", partita.getStanzaCorrente().getNome());
		assertEquals(19, partita.getGiocatore().getCfu());
		assertTrue(partita.getStanzaCorrente().hasAttrezzo("cucchiaio"));

		this.comando = factory.costruisciComando("prendi cucchiaio", io);
		comando.esegui(partita);
		assertFalse(partita.getStanzaCorrente().hasAttrezzo("cucchiaio"));
		assertEquals(2, partita.getGiocatore().getBorsa().getContenutoOrdinatoPerPeso().size());
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("cucchiaio"));
		assertEquals(2, partita.getGiocatore().getBorsa().getPeso());

		this.comando = factory.costruisciComando("vai ovest", io);
		comando.esegui(partita);
		assertEquals("bagno", partita.getStanzaCorrente().getNome());
		assertEquals(18, partita.getGiocatore().getCfu());
		assertTrue(partita.getStanzaCorrente().hasAttrezzo("torcia"));

		this.comando = factory.costruisciComando("prendi torcia", io);
		comando.esegui(partita);
		assertFalse(partita.getStanzaCorrente().hasAttrezzo("torcia"));
		assertEquals(3, partita.getGiocatore().getBorsa().getContenutoOrdinatoPerPeso().size());
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("torcia"));
		assertEquals(3, partita.getGiocatore().getBorsa().getPeso());

		this.comando = factory.costruisciComando("posa cucchiaio", io);
		comando.esegui(partita);
		assertEquals(2, partita.getGiocatore().getBorsa().getContenutoOrdinatoPerPeso().size());
		assertEquals(2, partita.getGiocatore().getBorsa().getPeso());
		assertTrue(partita.getStanzaCorrente().hasAttrezzo("cucchiaio"));
		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo("cucchiaio"));

		this.comando = factory.costruisciComando("vai est", io);
		comando.esegui(partita);
		assertEquals("salone", partita.getStanzaCorrente().getNome());
		assertEquals(17, partita.getGiocatore().getCfu());

		this.comando = factory.costruisciComando("vai nord", io);
		comando.esegui(partita);
		assertEquals("cameraDaLetto", partita.getStanzaCorrente().getNome());
		assertEquals(16, partita.getGiocatore().getCfu());
		assertEquals("Qui c'è buio pesto", this.partita.getStanzaCorrente().getDescrizione());
		StanzaBuia buia = (StanzaBuia) this.partita.getStanzaCorrente();
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo(buia.getAttrezzoIlluminante()));
		assertEquals("torcia", buia.getAttrezzoIlluminante());

		this.comando = factory.costruisciComando("posa torcia", io);
		comando.esegui(partita);
		assertEquals(1, partita.getGiocatore().getBorsa().getContenutoOrdinatoPerPeso().size());
		assertEquals(1, partita.getGiocatore().getBorsa().getPeso());
		assertTrue(partita.getStanzaCorrente().hasAttrezzo("torcia"));
		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo("torcia"));
		assertNotEquals("Qui c'è un buoi pesto", this.partita.getStanzaCorrente().getDescrizione());

		this.comando = factory.costruisciComando("vai est", io);
		comando.esegui(partita);
		assertEquals("studio", partita.getStanzaCorrente().getNome());
		assertEquals(15, partita.getGiocatore().getCfu());
		assertFalse(partita.getStanzaCorrente().hasAttrezzo("penna"));
		// assertEquals(1*2,partita.getStanzaCorrente().getAttrezzo("adaps").getPeso());

		this.comando = factory.costruisciComando("posa spada", io);
		comando.esegui(partita);
		// assertEquals(1,partita.getGiocatore().getBorsa().getContenutoOrdinatoPerPeso().size());
		// assertEquals(1,partita.getGiocatore().getBorsa().getPeso());
		assertFalse(partita.getStanzaCorrente().hasAttrezzo("spada"));
		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo("spada"));
		assertTrue(partita.getGiocatore().getBorsa().isEmpty());

		this.comando = factory.costruisciComando("vai ovest", io);
		comando.esegui(partita);
		assertEquals("cameraDaLetto", partita.getStanzaCorrente().getNome());
		assertEquals(14, partita.getGiocatore().getCfu());

		this.comando = factory.costruisciComando("prendi pass", io);
		comando.esegui(partita);
		assertFalse(partita.getStanzaCorrente().hasAttrezzo("pass"));
		assertEquals(1, partita.getGiocatore().getBorsa().getContenutoOrdinatoPerPeso().size());
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("pass"));
		assertEquals(2, partita.getGiocatore().getBorsa().getPeso());
		assertFalse(partita.getGiocatore().getBorsa().isEmpty());

		this.comando = factory.costruisciComando("vai sud", io);
		comando.esegui(partita);
		assertEquals("salone", partita.getStanzaCorrente().getNome());
		assertEquals(13, partita.getGiocatore().getCfu());

		this.comando = factory.costruisciComando("vai sud", io);
		comando.esegui(partita);
		assertEquals("ingresso", partita.getStanzaCorrente().getNome());
		assertEquals(12, partita.getGiocatore().getCfu());

		this.comando = factory.costruisciComando("vai est", io);
		comando.esegui(partita);
		assertEquals("bunker", partita.getStanzaCorrente().getNome());
		assertEquals(11, partita.getGiocatore().getCfu());
		assertEquals(partita.getStanzaCorrente(), partita.getStanzaCorrente().getStanzaAdiacente("est"));

		this.comando = factory.costruisciComando("vai est", io);
		comando.esegui(partita);
		assertEquals(10, partita.getGiocatore().getCfu());

		this.comando = factory.costruisciComando("posa pass", io);
		comando.esegui(partita);
		assertTrue(partita.getStanzaCorrente().hasAttrezzo("pass"));
		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo("pass"));
		assertEquals(labirinto.getStanze().get("uscita"), partita.getStanzaCorrente().getStanzaAdiacente("est"));

		this.comando = factory.costruisciComando("vai est", io);
		comando.esegui(partita);
		assertEquals(9, partita.getGiocatore().getCfu());
		assertTrue(partita.giocatoreIsVivo());
		assertEquals("uscita", partita.getStanzaCorrente().getNome());
		assertTrue(partita.vinta());
		assertTrue(partita.isFinita());
	}

	@Test
	public void comandiNonValidi() {
		this.comando = factory.costruisciComando("", io);
		comando.esegui(partita);
		assertNull(comando.getNome());

		this.comando = factory.costruisciComando("vado nord", io);
		comando.esegui(partita);
		assertEquals("ingresso", partita.getStanzaCorrente().getNome());
		assertEquals(20, partita.getGiocatore().getCfu());
		assertTrue(partita.getStanzaCorrente().getMapStanzeAdiacenti().containsKey("nord"));

		this.comando = factory.costruisciComando("vado nord", io);
		comando.esegui(partita);
		assertFalse(partita.getStanzaCorrente().getMapStanzeAdiacenti().containsKey("sud"));
		assertEquals("ingresso", partita.getStanzaCorrente().getNome());

		this.comando = factory.costruisciComando("vai", io);
		comando.esegui(partita);
		assertNull(comando.getParametro());
		assertEquals("ingresso", partita.getStanzaCorrente().getNome());

		this.comando = factory.costruisciComando("prendi", io);
		comando.esegui(partita);
		assertNull(comando.getParametro());
		assertTrue(partita.getStanzaCorrente().hasAttrezzo("spada"));
		assertTrue(partita.getGiocatore().getBorsa().isEmpty());

		this.comando = factory.costruisciComando("prendo spada", io);
		comando.esegui(partita);
		assertTrue(partita.getGiocatore().getBorsa().isEmpty());
		assertNull("spada", comando.getParametro());
		assertTrue(partita.getStanzaCorrente().hasAttrezzo("spada"));

		this.comando = factory.costruisciComando("prendi cucchiaio", io);
		comando.esegui(partita);
		assertTrue(partita.getGiocatore().getBorsa().isEmpty());
		assertFalse(partita.getStanzaCorrente().hasAttrezzo("cucchiaio"));

		this.comando = factory.costruisciComando("posa", io);
		comando.esegui(partita);
		assertNull(comando.getParametro());
		assertEquals(1, partita.getStanzaCorrente().getAttrezzi().size());

		this.comando = factory.costruisciComando("prendi spada", io);
		comando.esegui(partita);
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("spada"));
		assertFalse(partita.getStanzaCorrente().hasAttrezzo("spada"));

		this.comando = factory.costruisciComando("poso spada", io);
		comando.esegui(partita);
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("spada"));
		assertFalse(partita.getStanzaCorrente().hasAttrezzo("spada"));
		assertFalse(partita.getGiocatore().getBorsa().isEmpty());

		this.comando = factory.costruisciComando("posa cucchiaio", io);
		comando.esegui(partita);
		assertFalse(partita.getStanzaCorrente().hasAttrezzo("cucchiaio"));

	}

}
