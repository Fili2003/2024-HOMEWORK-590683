package it.uniroma3.diadia;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

/**
 * Classe principale di diadia, un semplice gioco di ruolo ambientato al dia.
 * Per giocare crea un'istanza di questa classe e invoca il letodo gioca
 *
 * Questa e' la classe principale crea e istanzia tutte le altre
 *
 * @author docente di POO (da un'idea di Michael Kolling and David J. Barnes)
 * 
 * @version base
 */

public class DiaDia {

	static final private String MESSAGGIO_BENVENUTO = ""
			+ "Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n"
			+ "Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n"
			+ "I locali sono popolati da strani personaggi, " + "alcuni amici, altri... chissa!\n"
			+ "Ci sono attrezzi che potrebbero servirti nell'impresa:\n"
			+ "puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n"
			+ "o regalarli se pensi che possano ingraziarti qualcuno.\n\n"
			+ "Per conoscere le istruzioni usa il comando 'aiuto'.";

	static final private String[] elencoComandi = { "vai", "aiuto", "prendi <attrezzo>", "posa <attrezzo>", "fine" };

	private Partita partita;
	private IOConsole interfaccia;

	public DiaDia(IOConsole interfaccia) {
		this.partita = new Partita(); // creo partita
		this.interfaccia = interfaccia;
	}

	// messaggio che va avanti ogni volta
	public void gioca() {
		String istruzione;
		// Scanner scannerDiLinee;

		interfaccia.mostraMessaggio(MESSAGGIO_BENVENUTO);
		// scannerDiLinee = new Scanner(interfaccia.leggiRiga());
		do
			istruzione = interfaccia.leggiRiga();
		while (!processaIstruzione(istruzione) && this.partita.getGiocatore().getCfu() > 0);
		// scannerDiLinee.close();
	}

	/**
	 * Processa una istruzione
	 *
	 * @return true se l'istruzione e' eseguita e il gioco continua, false
	 *         altrimenti
	 */
	private boolean processaIstruzione(String istruzione) {
		if (istruzione.isBlank())
			return false; // per risolvere problema del di invio senza scrivere niente
		Comando comandoDaEseguire = new Comando(istruzione);

		switch (comandoDaEseguire.getNome()) {
		case "fine":
			this.fine();
			return true;
		case "vai":
			this.vai(comandoDaEseguire.getParametro());
			break;
		case "aiuto":
			this.aiuto();
			break;
		case "prendi":
			this.prendi(comandoDaEseguire.getParametro());
			break;
		case "posa":
			this.posa(comandoDaEseguire.getParametro());
			break;
		default:
			interfaccia.mostraMessaggio("Comando sconosciuto");
			break;
		}
		if (this.partita.vinta()) {
			interfaccia.mostraMessaggio("Hai vinto!");
			return true;
		} else {
			if (this.partita.isFinita())
				interfaccia.mostraMessaggio("Hai perso chicco troppe mosse usate!! RIPROVA");
			return false;
		}
	}

	// implementazioni dei comandi dell'utente:

	/**
	 * Stampa informazioni di aiuto.
	 */
	private void aiuto() {
		for (int i = 0; i < elencoComandi.length; i++)
			System.out.print(elencoComandi[i] + " ");
		interfaccia.mostraMessaggio("");
	}

	/**
	 * Cerca di andare in una direzione. Se c'e' una stanza ci entra e ne stampa il
	 * nome, altrimenti stampa un messaggio di errore
	 */
	private void vai(String direzione) {
		if (direzione == null)
			interfaccia.mostraMessaggio("Dove vuoi andare ?");
		Stanza prossimaStanza = null; // inizializza a null la prossima stanza
		prossimaStanza = this.partita.getStanzaCorrente().getStanzaAdiacente(direzione);
		if (prossimaStanza == null)
			interfaccia.mostraMessaggio("Direzione inesistente");
		else {
			// cfu diminuiscono ogni volta che mi muovo in una stanza in teoria i cfu
			// iniziali sono 20 quindi al massimo potrei fare 20 mosse
			this.partita.setStanzaCorrente(prossimaStanza);
			int cfu = this.partita.getGiocatore().getCfu();
			this.partita.getGiocatore().setCfu(--cfu);
		}
		interfaccia.mostraMessaggio(
				partita.getStanzaCorrente().getDescrizione() + "\nCFU:" + partita.getGiocatore().getCfu());
	}

	/**
	 * Comando "Fine".
	 */
	private void fine() {
		interfaccia.mostraMessaggio("Grazie di aver giocato!"); // si desidera smettere
	}

	private void prendi(String nomeAttrezzo) {
		if (nomeAttrezzo == null)
			this.interfaccia.mostraMessaggio("Cosa vuoi prendere?");
		else {
			if (this.partita.getStanzaCorrente().hasAttrezzo(nomeAttrezzo)) {
				Attrezzo a = partita.getStanzaCorrente().getAttrezzo(nomeAttrezzo);
				if (partita.getGiocatore().getBorsa().addAttrezzo(a)) {// se lo aggiungo alla borsa lo rimuovo dalla stanza														
					partita.getStanzaCorrente().removeAttrezzo(a);
					this.interfaccia.mostraMessaggio(nomeAttrezzo + " aggiunto alla borsa");
				} else
					this.interfaccia.mostraMessaggio("Borsa piena");
			} else
				this.interfaccia.mostraMessaggio(nomeAttrezzo + " assente nella stanza");
		}
		this.interfaccia.mostraMessaggio(this.partita.getGiocatore().getBorsa().toString());
	}

	private void posa(String nomeAttrezzo) {

		if (nomeAttrezzo == null)
			this.interfaccia.mostraMessaggio("Nulla da posare");
		else {
			if (!partita.getGiocatore().getBorsa().hasAttrezzo(nomeAttrezzo))
				this.interfaccia.mostraMessaggio("Attrezzo non presenta nella borsa per posarlo");
			else {
				Attrezzo a = partita.getGiocatore().getBorsa().getAttrezzo(nomeAttrezzo);

				if (partita.getStanzaCorrente().addAttrezzo(a)) { // se posso aggiunge lo rimuovo
					partita.getGiocatore().getBorsa().removeAttrezzo(nomeAttrezzo);
					this.interfaccia.mostraMessaggio(nomeAttrezzo + " posato nella " + partita.getStanzaCorrente());
				} else
					this.interfaccia.mostraMessaggio("Non è stato possibile aggiungere l'attrezzo");
			}
		}
		this.interfaccia.mostraMessaggio(this.partita.getStanzaCorrente().getDescrizione()); // STAMPO LA STANZA
	}

	public static void main(String[] argc) {
		IOConsole interfaccia = new IOConsole();
		DiaDia gioco = new DiaDia(interfaccia);
		gioco.gioca();
	}
}
