package ambientiTest;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;

import it.uniroma3.diadia.ambienti.Direzione;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class StanzaTest {
	private Stanza stanza;
	private Stanza vuota;
	private Stanza adiacente;
	private Attrezzo attrezzo;
	
	@Before
	public void setUp() {
		vuota = new Stanza("vuota");
		stanza = new Stanza("nonVuota");
		adiacente = new Stanza("Dodo");
		attrezzo = new Attrezzo("coltello",0);
		stanza.impostaStanzaAdiacente(Direzione.est, adiacente);
		stanza.addAttrezzo(attrezzo);	
	}
	
//TEST PER getStanzaAdiacente
	
    @Test //stanza adicente assente
	public void testGetStanzaAdiacenteVuota() {
    	assertNull(vuota.getStanzaAdiacente(null));
	}
	@Test //stanza impostata
	public void testGetStanzaAdicenteNonVuotaPresente() {
		assertNotNull(stanza.getStanzaAdiacente(Direzione.est));
	}
	
	@Test //stanza nome 
	public void testGetStanzaAdiacentePresenteAvantiIndietro() {
		adiacente.impostaStanzaAdiacente(Direzione.ovest, stanza);
		assertEquals(stanza.getStanzaAdiacente(Direzione.ovest),adiacente.getStanzaAdiacente(Direzione.est));
	}
 
// TEST PER getAttrezzo
	
	@Test // stanza vuota
	public void testGetAttrezzoStanzaVuota() {
		assertNull(vuota.getAttrezzo("inesistente"));
	}
	@Test // stanza non vuota
	public void testGetAttrezzoStanzaNonVuotaPresente() {
		assertNotNull(stanza.getAttrezzo("coltello"));
	}
	@Test // stanza 
	public void testGetAttrezzoStanzaNonVuotaPresenteAttrezzoNonPresente() {
		assertNull(stanza.getAttrezzo("nomeAttrezzoNonPresente"));
	}
	
// TEST per hasAttrezzo

	@Test //non c'è l'attrezzo
	public void testHasAttrezzoNonPresente() {
		assertFalse(vuota.hasAttrezzo("attrezzoNonCe"));
	}
	@Test // c'è l'attrezzo 
	public void testHasAttrezzoPresente() {
		assertTrue(stanza.hasAttrezzo("coltello"));
	}
	@Test //c'è l'attrezzo nella stanza ma non quello che gli passo
	public void testHasAttrezzoPresentMaNonQuelNome() {
		assertFalse(stanza.hasAttrezzo("lama"));
	}




}
