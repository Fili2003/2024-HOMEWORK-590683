package uniRoma3;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.giocatore.Borsa;

public class BorsaTest {

	private Borsa borsa;
	private Attrezzo attrezzo;
	private Borsa borsa2;

	@Before
	public void setUp() {
		this.borsa = new Borsa();
		this.borsa2 = new Borsa();
		this.attrezzo = new Attrezzo("coltello", 1);
		borsa2.addAttrezzo(attrezzo);
 
	}

//TEST GETPESO
//devo fare dei test con AssertNULL ma hanno senso con getPeso ? perche java inizializza tutto da solo
	@Test
	public void testgetPeso10() {
		assertEquals(10, borsa.getPesoMax());
	}

	@Test
	public void testgetPesoIniziale() {
		assertEquals(0, borsa.getPeso());
	}

	@Test
	public void testgetPesoInBaseAttrezziAggiunti() {
		assertEquals(1, borsa2.getPeso());
	}

//TEST REMOVEATTREZZO
	@Test
	public void removeAttrezzoNulloVuoto() {
		assertNull(borsa.removeAttrezzo(null));
	}

	@Test
	public void removeAttrezzoInBaseAlNome() {
		assertEquals(attrezzo, borsa2.removeAttrezzo("coltello"));
	}

	@Test
	public void removeAttrezzoNoNomeDiverso() {
		assertNotEquals(attrezzo, borsa2.removeAttrezzo("chiodo"));
	}

// TEST PER ADDATTREZZO
	@Test
	public void addAttrezzoNull() {
		assertFalse(borsa.addAttrezzo(null)); // perche errore ?
	}

	@Test
	public void addAttrezzoNoPerchePesoMax() {
		this.attrezzo = new Attrezzo("lama", 12);
		assertFalse(borsa2.addAttrezzo(attrezzo));
	}

// TEST EMPTY
	@Test
	public void isEmpty() {
		assertTrue(borsa.isEmpty());
	}

	@Test
	public void isNotEmpty() {
		assertFalse(borsa2.isEmpty());
	}
}
