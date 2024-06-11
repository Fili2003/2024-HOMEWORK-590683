package ambientiTest;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;

import it.uniroma3.diadia.ambienti.Direzione;
import it.uniroma3.diadia.ambienti.LabirintoBuilder;

public class LabirintoBuilderTest {

	private LabirintoBuilder lab;
	
	@Before
	public void setUp() {
		this.lab = new LabirintoBuilder();
	
	}

	@Test
	public void testAddStanzaInizialeNulla() {
		assertNull(lab.getStanzaIniziale());
	}
	
	@Test
	public void testAddStanzaInizialeNonNulla() {
		lab.addStanzaIniziale("iniziale").getLabirinto();
		assertEquals("iniziale",lab.getStanzaIniziale().getNome());
	}

	@Test
	public void testAddStanzaVincenteNulla() {
		assertNull(lab.getStanzaVincente());	
	}
	
	@Test
	public void testAddStanzaVincenteNonNulla() {
		lab.addStanzaVincente("vincente").getLabirinto();
		assertEquals("vincente",lab.getStanzaVincente().getNome());
	}


	@Test
	public void testAddAttrezzoNellaStanzaUnica() {
		lab.addStanzaIniziale("Prima").addAttrezzo("attrezzo", 1);
		assertTrue(lab.getStanzaIniziale().hasAttrezzo("attrezzo"));
	}
	@Test
	public void testAddAttrezzoNellaStanzaDoppia() {
		lab.addStanzaIniziale("Prima").addStanzaVincente("seconda").addAttrezzo("attrezzo", 1);
		assertFalse(lab.getStanzaIniziale().hasAttrezzo("attrezzo"));
		assertTrue(lab.getStanzaVincente().hasAttrezzo("attrezzo"));
	}

	@Test
	public void testAddAdiacenzaSecondaStanzaNull() {
		lab.addStanzaIniziale("prima").addAdiacenza("prima", null, Direzione.ovest);
		assertNull(lab.getStanzaIniziale().getStanzaAdiacente(Direzione.ovest));
	}
	@Test
	public void testAddAdiacenzaCheImpostaInModoCorretto() {
		lab.addStanzaIniziale("prima").addStanza("seconda").addAdiacenza("prima", "seconda", Direzione.ovest);
		assertEquals("seconda", lab.getStanzaIniziale().getStanzaAdiacente(Direzione.ovest).getNome());
	}

	@Test
	public void testAddStanzaNulla() {
		lab.addStanza(null);
		assertTrue(lab.getStanze().isEmpty());
	}
	@Test
	public void testAddStanzaAggiunta() {
		lab.addStanza("prima");
		assertFalse(lab.getStanze().isEmpty());
	}
	

}
