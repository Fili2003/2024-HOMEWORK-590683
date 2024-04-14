package uniRoma3;

import static org.junit.Assert.*;


import org.junit.Test;

import it.uniroma3.diadia.DiaDia;
import it.uniroma3.diadia.IOConsole;

import org.junit.*;
public class DiaDiaTest {

	private DiaDia diadiaTest;
	private IOConsole interfacciaTest;
	
	@Before
	public void setUp() {
		this.diadiaTest = new DiaDia(interfacciaTest);
		
	}
	
	@Test
	public void testPrendiNull() {
		assertFalse(diadiaTest.prendi(null));
	}
	@Test
	public void testPrendiTrue() {
		assertTrue(diadiaTest.prendi("osso"));
	}
	@Test
	public void testPrendiQualcosaCheNonCe() {
		assertFalse(diadiaTest.prendi("coltello"));
	}
	
	@Test
	public void testPosaNull() {
		assertFalse(diadiaTest.posa(null));
		
	}
	@Test 
	public void testPosaOggetoCheHo() {
		diadiaTest.prendi("osso");
		assertTrue(diadiaTest.posa("osso"));
	}
	@Test 
	public void testPosaOggetoCheNonHo() {
		assertFalse(diadiaTest.posa("osso"));
	}
	

}
