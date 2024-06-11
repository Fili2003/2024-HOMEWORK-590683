package comandiTest;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.*;
import org.junit.Test;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.labirinto;
import it.uniroma3.diadia.comandi.ComandoVai;

public class ComandoVaiTest {
	
	
	private ComandoVai comando;
	private Partita partita;
	private IO io;
	private labirinto labirinto;
	private Scanner scanner;

	
	@Before
	public void setUp() {
		this.partita = new Partita(labirinto);
		this.io = new IOConsole(scanner);
		this.comando = new ComandoVai("vai",io);
	}

	@Test
	public void testEseguiSud() {
		this.comando.setParametro("sud");
		this.comando.esegui(partita);
		assertEquals("Aula N10",partita.getStanzaCorrente().getNome());
	}
	@Test
	public void testEseguiOvest() {
		this.comando.setParametro("ovest");
		this.comando.esegui(partita);
		assertEquals("Laboratorio Campus",partita.getStanzaCorrente().getNome());
	}
	
	@Test
	public void testEseguiNord() {
		this.comando.setParametro("nord");
		this.comando.esegui(partita);
		assertEquals(it.uniroma3.diadia.ambienti.labirinto.bilbioteca,partita.getStanzaCorrente().getNome());
	}
	
	@Test 
	public void testEseguiEst() {
		this.comando.setParametro("est");
		this.comando.esegui(partita);
		assertEquals("Aula N11",partita.getStanzaCorrente().getNome());
	}
	
	@Test
	public void testEseguiSbagliato() {
		this.comando = new ComandoVai("pippo",io);
		this.comando.esegui(partita);
		assertEquals("Atrio",partita.getStanzaCorrente().getNome());
	}
	
}
