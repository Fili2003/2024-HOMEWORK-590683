package it.uniroma3.diadia;

import java.util.Scanner;

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

	private DiaDia () {
		this.partita = new Partita(); // creo partita
	}
	
	public DiaDia(IOConsole interfaccia) {
		this.partita = new Partita(); // creo partita
		this.interfaccia=interfaccia;
	}

	// messaggio che va avanti ogni volta
	public void gioca() {
		String istruzione;
		//Scanner scannerDiLinee;

		interfaccia.mostraMessaggio(MESSAGGIO_BENVENUTO);
		//scannerDiLinee = new Scanner(interfaccia.leggiRiga());
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
			if (this.prendi(comandoDaEseguire.getParametro()))
				interfaccia.mostraMessaggio(comandoDaEseguire.getParametro() + " è nella tua borsa");
			else
				interfaccia.mostraMessaggio("Oggetto non è nella stanza");
			break;
		case "posa":
			if (this.posa(comandoDaEseguire.getParametro()))
				interfaccia.mostraMessaggio(comandoDaEseguire.getParametro() + " posato nella stanza");
			else
				interfaccia.mostraMessaggio("Oggetto non è nella tua borsa");
			break;
		default:
			interfaccia.mostraMessaggio("Comando sconosciuto");
			break;
		}

//		if (comandoDaEseguire.getNome().equals("fine")) {
//			this.fine();
//			return true;
//		} else if (comandoDaEseguire.getNome().equals("vai"))
//			this.vai(comandoDaEseguire.getParametro());
//		else if (comandoDaEseguire.getNome().equals("aiuto")) // print array comandi
//			this.aiuto();
//		else
//			interfaccia.mostraMessaggio("Comando sconosciuto");
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
			interfaccia.mostraMessaggio(elencoComandi[i] + " ");
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
		interfaccia.mostraMessaggio(partita.getStanzaCorrente().getDescrizione() + "\nCFU:" + partita.getGiocatore().getCfu());
	}

	/**
	 * Comando "Fine".
	 */
	private void fine() {
		interfaccia.mostraMessaggio("Grazie di aver giocato!"); // si desidera smettere
	}

//USO REMOATTREZZO CHE RESTITUISCE UN BOOLEAN
	public boolean prendi(String nomeAttrezzo) {
//verifico che ho l'attrezzo nella stanza
		if (!partita.getStanzaCorrente().hasAttrezzo(nomeAttrezzo))
			return false;
		Attrezzo a = partita.getStanzaCorrente().getAttrezzo(nomeAttrezzo); // me lo salvo

		if (partita.getStanzaCorrente().removeAttrezzo(a)) {
			partita.getGiocatore().getBorsa().addAttrezzo(a); // lo aggiungo alla borsa
			return true;
		}
		return false;
	}

//USO REMOATTREZZO CHE RESTITUISCE UN ATTREZZO
	public boolean posa(String nomeAttrezzo) {
//verifico che ho l'attrezzo in borsa
		if (!partita.getGiocatore().getBorsa().hasAttrezzo(nomeAttrezzo))
			return false;

		Attrezzo a = partita.getGiocatore().getBorsa().removeAttrezzo(nomeAttrezzo); // me lo salvo

		partita.getStanzaCorrente().addAttrezzo(a); // lo aggiungo alla stanza

		return true;

	}

	public static void main(String[] argc) {
		IOConsole interfaccia = new IOConsole();
		DiaDia gioco = new DiaDia(interfaccia);
		gioco.gioca();
	}
}
