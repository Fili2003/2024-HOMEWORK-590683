package uniRoma3;

import static org.junit.Assert.*;

import org.junit.Test;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.ambienti.StanzaBloccata;
import it.uniroma3.diadia.attrezzi.Attrezzo;

import org.junit.*;

public class StanzaBloccataTest {

	private StanzaBloccata blocked;
	private Attrezzo attrezzo;
	private Stanza sAd;

	@Before
	public void setUp() {
		this.blocked = new StanzaBloccata("bloccata", "nord", "chiave");
		this.sAd = new Stanza("sAdiacente");
	}

	@Test
	public void testGetStanzaAdiacentSbloccata() {
		this.attrezzo = new Attrezzo("chiave", 1);
		blocked.addAttrezzo(attrezzo);
		blocked.impostaStanzaAdiacente("nord", sAd);
		assertEquals("sAdiacente", blocked.getStanzaAdiacente("nord").getNome());
	}

	@Test
	public void testGetStanzaAdiacentBloccata() {
		blocked.impostaStanzaAdiacente("nord", sAd);
		assertEquals(blocked, blocked.getStanzaAdiacente("nord"));
	}

	@Test
	public void testGetDescrizioneStanzaBloccata() {
		assertEquals("Se non è presente l'attrezzochiavenella stanza essa è bloccata", blocked.getDescrizione());
	}

	@Test
	public void testGetDescrizioneStanzaNonBloccata() {
		this.attrezzo = new Attrezzo("chiave", 1);
		blocked.addAttrezzo(attrezzo);
		assertEquals(blocked.toString(), blocked.getDescrizione());
	}
}
