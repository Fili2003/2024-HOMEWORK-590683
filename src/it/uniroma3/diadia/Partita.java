package it.uniroma3.diadia;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.ambienti.labirinto;
import it.uniroma3.diadia.giocatore.Giocatore;

/**
 * Questa classe modella una partita del gioco
 *
 * @author docente di POO
 * @see Stanza
 * @version base
 */

public class Partita {

	private Stanza stanzaCorrente;
	private boolean finita;

	private labirinto labirinto;
	private Giocatore giocatore;

	public Partita(labirinto labirinto) {
		this.labirinto=labirinto;
		this.giocatore = new Giocatore();
		this.stanzaCorrente = this.labirinto.getStanzaIniziale();
		this.finita = false;

	}

	public labirinto getLabirinto() {
		return this.labirinto;
	}

	/**
	 * Crea tutte le stanze e le porte di collegamento
	 */

	public void setStanzaCorrente(Stanza stanzaCorrente) {
		this.stanzaCorrente = stanzaCorrente;
	}

	public Stanza getStanzaCorrente() {
		return this.stanzaCorrente;
	}

	public Giocatore getGiocatore() {
		return giocatore;
	}

	/**
	 * Restituisce vero se e solo se la partita e' stata vinta
	 * 
	 * @return vero se partita vinta
	 */
	public boolean vinta() {
		return this.getStanzaCorrente() == this.labirinto.getStanzaVincente();
	}

	/**
	 * Restituisce vero se e solo se la partita e' finita
	 * 
	 * @return vero se partita finita
	 */
	public boolean isFinita() {
		return finita || vinta() || (this.giocatore.getCfu() == 0);
	}

	/**
	 * Imposta la partita come finita
	 *
	 */
	public void setFinita() {
		this.finita = true;
	}

	public boolean giocatoreIsVivo() {

		return this.giocatore.getCfu() > 0;
	}
	public void setLabirinto(labirinto labirinto) {
		this.labirinto=labirinto;
	}

}
