package ambientiTest;

import static org.junit.Assert.*;

import org.junit.Test;


import it.uniroma3.diadia.ambienti.StanzaBuia;
import it.uniroma3.diadia.attrezzi.Attrezzo;

import org.junit.*;

public class StanzaBuiaTest {

	private StanzaBuia nonLuminosa;
	private Attrezzo attrezzoLuminoso;

	@Before
	public void setUp() {
		this.nonLuminosa = new StanzaBuia("nonLuce", "luce");

	}

	@Test
	public void testGetDescrizioneStanzaBuia() {
		assertEquals("Qui c'è buio pesto",nonLuminosa.getDescrizione());
	}
	
	@Test
	public void testGetDescrizioneStanzaNonBuia() {
		this.attrezzoLuminoso = new Attrezzo("luce",1);
		nonLuminosa.addAttrezzo(attrezzoLuminoso);
		assertEquals(nonLuminosa.toString(),nonLuminosa.getDescrizione());
	}
	

}
