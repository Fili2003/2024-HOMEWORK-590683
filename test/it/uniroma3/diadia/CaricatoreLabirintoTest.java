package it.uniroma3.diadia;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.ambienti.Direzione;

//this.leggiECreaStanze();
//this.leggiInizialeEvincente();
//this.leggiECollocaAttrezzi();
//this.leggiEImpostaUscite();
//this.leggiStanzaBuia();
//this.leggiStanzaMagica();
//this.leggiStanzaBloccata();
//this.leggiECreaMaghi();
//this.leggiECreaCani();
//this.leggiECreaStreghe();
public class CaricatoreLabirintoTest {

	private CaricatoreLabirinto c1;
	private CaricatoreLabirinto c2;
	private CaricatoreLabirinto c3;

	private final String monolocale = "Stanze:camera\n" + "Inizio:camera\n" + "Vincente:camera\n"
			+ "Attrezzi:telecomando 1 camera\n" + "Uscite:\n" + "Buia:\n" + "Magica:\n" + "Bloccata:\n"
			+ "PersonaggioMago:\n" + "PersonaggioCane:\n" + "PersonaggioStrega:\n";

	private final String bilocale = "Stanze:camera,bagno\n" + "Inizio:camera\n" + "Vincente:bagno\n"
			+ "Attrezzi:sapone 2 bagno\n" + "Uscite:camera nord bagno,bagno sud camera\n" + "Buia:\n" + "Magica:\n"
			+ "Bloccata:\n" + "PersonaggioMago:\n" + "PersonaggioCane:\n" + "PersonaggioStrega:\n";

	private final String trilocale = "Stanze:N10,biblioteca,N11\n" + "Inizio:N10\n" + "Vincente:biblioteca\n"
			+ "Attrezzi:Osso 5 N10\n" + "Uscite:N10 nord biblioteca,biblioteca sud N10\n"
			+ "Buia:N11 torcia\n" + "Magica:\n" + "Bloccata:\n" +" PersonaggioMago:\n"+ "PersonaggioCane:\n" + "PersonaggioStrega:\n";

	@Before
	public void setUp() throws Exception {
		this.c1 = new CaricatoreLabirinto(new StringReader(monolocale));
		this.c2 = new CaricatoreLabirinto(new StringReader(bilocale));
		this.c3 = new CaricatoreLabirinto(new StringReader(trilocale));
	}

	@Test
	public void testMonolocale() throws FormatoFileNonValidoException {
		c1.carica();
		assertFalse(this.c1.getStanze().isEmpty());
		assertEquals(1, this.c1.getStanze().size());
		assertEquals("camera", this.c1.getStanzaVincente().getNome());
		assertEquals("camera", this.c1.getStanzaVincente().getNome());
		assertTrue(this.c1.getStanzaIniziale().hasAttrezzo("telecomando"));
		assertEquals(1, this.c1.getStanzaVincente().getAttrezzo("telecomando").getPeso());
		assertTrue(this.c1.getStanze().containsKey("camera"));
	}

	@Test
	public void testBilocale() throws FormatoFileNonValidoException {
		c2.carica();
		assertFalse(this.c2.getStanze().isEmpty());
		assertEquals(2, this.c2.getStanze().size());
		assertEquals("camera", this.c2.getStanzaIniziale().getNome());
		assertEquals("bagno", this.c2.getStanzaVincente().getNome());
		assertTrue(this.c2.getStanzaVincente().hasAttrezzo("sapone"));
		assertEquals(2, this.c2.getStanzaVincente().getAttrezzo("sapone").getPeso());
		assertEquals("bagno", this.c2.getStanzaIniziale().getStanzaAdiacente(Direzione.nord).getNome());
		assertEquals("camera", this.c2.getStanzaVincente().getStanzaAdiacente(Direzione.sud).getNome());
	}

	@Test
	public void testTrilocale() throws FormatoFileNonValidoException {
		c3.carica();
		assertFalse(this.c3.getStanze().isEmpty());
		assertEquals(3, this.c3.getStanze().size());
		assertEquals("N10", this.c3.getStanzaIniziale().getNome());
		assertEquals("biblioteca", this.c3.getStanzaVincente().getNome());
		assertTrue(this.c3.getStanzaIniziale().hasAttrezzo("Osso"));
		assertEquals(5, this.c3.getStanzaIniziale().getAttrezzo("Osso").getPeso());
		assertEquals("biblioteca", this.c3.getStanzaIniziale().getStanzaAdiacente(Direzione.nord).getNome());
		assertEquals("N10", this.c3.getStanzaVincente().getStanzaAdiacente(Direzione.sud).getNome());
	}
}
