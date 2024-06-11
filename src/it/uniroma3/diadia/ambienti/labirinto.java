package it.uniroma3.diadia.ambienti;

import java.io.FileNotFoundException;

import it.uniroma3.diadia.CaricatoreLabirinto;
import it.uniroma3.diadia.FormatoFileNonValidoException;
import it.uniroma3.diadia.attrezzi.Attrezzo;

/**
 * Labirinto deve memorizzare la stanza inizale e quella finale aggiungere un
 * riferimento ad un'istanza di Labirinto nella classe Partita (che ovviamente
 * dovrà essere liberata dalle responsabilità spostate nella nuova classe)
 */
public class labirinto {
	private Stanza stanzaIniziale;
	private Stanza stanzaVincente;
	public final static String bilbioteca = "biblioteca";

	public labirinto() {
		creaStanze();
	}

	public labirinto(String nomeFile) throws FileNotFoundException, FormatoFileNonValidoException {
		CaricatoreLabirinto c = new CaricatoreLabirinto(nomeFile);
		c.carica();
		this.stanzaIniziale = c.getStanzaIniziale();
		this.stanzaVincente = c.getStanzaVincente();
	}

	private void creaStanze() {

		/* crea gli attrezzi */
		Attrezzo lanterna = new Attrezzo("lanterna", 3);
		Attrezzo osso = new Attrezzo("osso", 1);

		/* crea stanze del labirinto */
		Stanza atrio = new Stanza("Atrio");
		Stanza aulaN11 = new Stanza("Aula N11");
		Stanza aulaN10 = new Stanza("Aula N10");
		Stanza laboratorio = new Stanza("Laboratorio Campus");
		Stanza biblioteca = new Stanza("Biblioteca");

		/* collega le stanze */
		atrio.impostaStanzaAdiacente(Direzione.nord, biblioteca);
		atrio.impostaStanzaAdiacente(Direzione.est, aulaN11);
		atrio.impostaStanzaAdiacente(Direzione.sud, aulaN10);
		atrio.impostaStanzaAdiacente(Direzione.ovest, laboratorio);
		aulaN11.impostaStanzaAdiacente(Direzione.est, laboratorio);
		aulaN11.impostaStanzaAdiacente(Direzione.ovest, atrio);
		aulaN10.impostaStanzaAdiacente(Direzione.nord, atrio);
		aulaN10.impostaStanzaAdiacente(Direzione.est, aulaN11);
		aulaN10.impostaStanzaAdiacente(Direzione.ovest, laboratorio);
		laboratorio.impostaStanzaAdiacente(Direzione.est, atrio);
		laboratorio.impostaStanzaAdiacente(Direzione.ovest, aulaN11);
		biblioteca.impostaStanzaAdiacente(Direzione.sud, atrio);

		/* pone gli attrezzi nelle stanze */
		aulaN10.addAttrezzo(lanterna);
		atrio.addAttrezzo(osso);

		// il gioco comincia nell'atrio
		this.stanzaIniziale = atrio;
		this.stanzaVincente = biblioteca;
	}

	public Stanza getStanzaVincente() {
		return stanzaVincente;
	}

	public Stanza getStanzaIniziale() {
		return stanzaIniziale;
	}

	public void setStanzaIniziale(Stanza stanzaIniziale) {
		this.stanzaIniziale = stanzaIniziale;
	}

	public void setStanzaVincente(Stanza stanzaVincente) {
		this.stanzaVincente = stanzaVincente;
	}

}
