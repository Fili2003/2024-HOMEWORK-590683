package uniRoma3;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.*;
import org.junit.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.giocatore.Borsa;

public class BorsaTest {

	private Borsa borsaVuota;
	private Attrezzo attrezzo;
	private Attrezzo attrezzo2;
	private Attrezzo attrezzo3;
	private Attrezzo attrezzo4;
	private Borsa borsa2;
	private List<Attrezzo> listaVuota;
	private List<Attrezzo> listaNonVuota;
	private Set<Attrezzo> setVuoto;
	private Set<Attrezzo> setNonVuoto;
	private Set<Attrezzo> setNonVuoto2;
	private Map<Integer,Set<Attrezzo>> mappaVuota;
	private Map<Integer,Set<Attrezzo>> mappaNonVuota;


	@Before
	public void setUp() {
		this.borsaVuota = new Borsa();
		this.borsa2 = new Borsa();
		this.attrezzo = new Attrezzo("coltello", 1);
		this.attrezzo2 = new Attrezzo("Lama", 3);
		borsa2.addAttrezzo(attrezzo);
		this.listaNonVuota = new ArrayList<>();
		this.setVuoto = new TreeSet<>();
		this.setNonVuoto = new TreeSet<Attrezzo>();
		this.setNonVuoto2 = new HashSet<Attrezzo>();
		this.mappaVuota = new HashMap<Integer, Set<Attrezzo>>();
		this.mappaNonVuota = new HashMap<Integer, Set<Attrezzo>>();
		listaNonVuota.add(attrezzo);
		setNonVuoto.add(attrezzo);
		setNonVuoto2.add(attrezzo);
		mappaNonVuota.put(1, setNonVuoto);
	}

//TEST GETPESO
	@Test
	public void testgetPeso10() {
		assertEquals(10, borsaVuota.getPesoMax());
	}

	@Test
	public void testgetPesoIniziale() {
		assertEquals(0, borsaVuota.getPeso());
	}

	@Test
	public void testgetPesoInBaseAttrezziAggiunti() {
		assertEquals(1, borsa2.getPeso());
	}

//TEST REMOVEATTREZZO
	@Test
	public void removeAttrezzoNulloVuoto() {
		assertNull(borsaVuota.removeAttrezzo(null));
	}

	@Test
	public void removeAttrezzoInBaseAlNome() {
		assertEquals(attrezzo, borsa2.removeAttrezzo("coltello"));
	}

	@Test
	public void removeAttrezzoNoNomeDiverso() {
		borsa2.addAttrezzo(attrezzo2);
		assertNotEquals(attrezzo, borsa2.removeAttrezzo("Lama"));
	}

// TEST PER ADDATTREZZO
	@Test
	public void addAttrezzoNull() {
		assertFalse(borsaVuota.addAttrezzo(null));
	}

	@Test
	public void addAttrezzoNoPerchePesoMax() {
		this.attrezzo = new Attrezzo("lama", 12);
		assertFalse(borsa2.addAttrezzo(attrezzo));
	}

// TEST EMPTY
	@Test
	public void isEmpty() {
		assertTrue(borsaVuota.isEmpty());
	}

	@Test
	public void isNotEmpty() {
		assertFalse(borsa2.isEmpty());
	}

// TEST PER getContenutoOrdinatoPerPeso

	@Test
	public void getContenutoOrdinatoPerPesoSuListaVuota() {
		this.listaVuota = new ArrayList<>(); 
		assertEquals(listaVuota, borsaVuota.getContenutoOrdinatoPerPeso());
	}

	@Test
	public void getContenutoOrdinatoPerPesoSuListaUnElemento() {
		assertEquals(listaNonVuota, borsa2.getContenutoOrdinatoPerPeso());
	}

	@Test
	public void getContenutoOrdinatoPerPesoSuListaPiuDiUnElemento() {
		this.attrezzo3 = new Attrezzo("Pistola", 6);
		this.attrezzo4 = new Attrezzo("lampada", 0);
		borsa2.addAttrezzo(attrezzo2);
		borsa2.addAttrezzo(attrezzo3);
		borsa2.addAttrezzo(attrezzo4);
		listaNonVuota.remove(0);
		listaNonVuota.add(attrezzo4);
		listaNonVuota.add(attrezzo); 
		listaNonVuota.add(attrezzo2);
		listaNonVuota.add(attrezzo3);
		assertEquals(listaNonVuota, borsa2.getContenutoOrdinatoPerPeso());
	}

	@Test
	public void getContenutoOrdinatoPerPesoSuListaConElementiStessoPeso() {
		this.attrezzo3 = new Attrezzo("Pistola", 3);
		borsa2.addAttrezzo(attrezzo2);
		borsa2.addAttrezzo(attrezzo3);
		listaNonVuota.add(attrezzo2); 
		listaNonVuota.add(attrezzo3);

		assertEquals(listaNonVuota, borsa2.getContenutoOrdinatoPerPeso());
	}

// 	TEST per getContenutoOrdinatoPerNome	

	@Test
	public void getContenutoOrdinatoPerNomeSuListaVuota() {
		assertEquals(setVuoto, borsaVuota.getContenutoOrdinatoPerNome());
	}
	@Test
	public void getContenutoOrdinatoPerNomeSuSetUnElemento() {
		assertEquals(setNonVuoto, borsa2.getContenutoOrdinatoPerNome());
	}
	@Test
	public void getContenutoOrdinatoPerNomeSuListaPiuDiUnElemento() {
		this.attrezzo2 = new Attrezzo("fiaccola",3);
		this.attrezzo3 = new Attrezzo("anatra",1);
		borsa2.addAttrezzo(attrezzo2);
		borsa2.addAttrezzo(attrezzo3);
		setNonVuoto.add(attrezzo2);
		setNonVuoto.add(attrezzo3); 
		assertEquals(setNonVuoto, borsa2.getContenutoOrdinatoPerNome());
	}
	
// TEST per getContenutoRaggruppatoPerPeso
	
	@Test
	public void getContenutoRaggruppatoPerPesoVuoto() {
		assertEquals(mappaVuota, borsaVuota.getContenutoRaggruppatoPerPeso());
	}
	
	@Test
	public void getContenutoRaggruppatoPerPesoDueElementiUguale() {
		this.attrezzo2 = new Attrezzo("Calcolatrice",1);
		borsa2.addAttrezzo(attrezzo2);
		setNonVuoto2.add(attrezzo2);
		mappaNonVuota.put(1, setNonVuoto);
		assertEquals(mappaNonVuota,borsa2.getContenutoRaggruppatoPerPeso());
	}
	
	@Test
	public void getContenutoRaggruppatoPerPesoDueElementiDiversi() {
		this.attrezzo2 = new Attrezzo("Calcolatrice",1);
		borsa2.addAttrezzo(attrezzo2);
		setNonVuoto2.add(attrezzo2);
		mappaNonVuota.put(1, setNonVuoto);
		assertEquals(mappaNonVuota,borsa2.getContenutoRaggruppatoPerPeso());
	}
	
// TEST per getSortedSetOrdinatoPerPeso
	@Test
	public void getSortedSetOrdinatoPerPesoVuoto() {
		assertEquals(setVuoto,borsaVuota.getSortedSetOrdinatoPerPeso());
	}
	@Test
	public void getSortedSetOrdinatoPerPesoUgualeQuindiNome() {
		this.attrezzo2 = new Attrezzo("Albero",1);
		borsa2.addAttrezzo(attrezzo2);
		setNonVuoto.add(attrezzo2);
		assertEquals(setNonVuoto,borsa2.getSortedSetOrdinatoPerPeso());
	}
	@Test
	public void getSortedSetOrdinatoPerPesoDiverso() {
		this.attrezzo2 = new Attrezzo("Albero",5);
		borsa2.addAttrezzo(attrezzo2);
		setNonVuoto.add(attrezzo2);
		assertEquals(setNonVuoto,borsa2.getSortedSetOrdinatoPerPeso());
	}
}
