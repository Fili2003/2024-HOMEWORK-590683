package it.uniroma3.diadia;

import java.util.Scanner;

import it.uniroma3.diadia.ambienti.Direzione;
import it.uniroma3.diadia.ambienti.LabirintoBuilder;
import it.uniroma3.diadia.ambienti.labirinto;
import it.uniroma3.diadia.comandi.FabbricaComandiRiflessiva;
import it.uniroma3.diadia.comandi.FabbricaDiComandi;
import it.uniroma3.diadia.comandi.abstractComando;

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

	public static final String[] elencoComandi = { "vai", "aiuto", "prendi <attrezzo>", "posa <attrezzo>", "guarda ",
			"fine" };

	private Partita partita;
	private IO interfaccia;

	public DiaDia(IO interfaccia, labirinto labirinto) {
		this.partita = new Partita(labirinto); // creo partita
		this.interfaccia = interfaccia;

	}

	public void gioca() {
		String istruzione;
		// Scanner scannerDiLinee;

		this.interfaccia.mostraMessaggio(MESSAGGIO_BENVENUTO);

		do
			istruzione = this.interfaccia.leggiRiga();
		while (!processaIstruzione(istruzione) && this.partita.getGiocatore().getCfu() > 0);
	}

	/**
	 * Processa una istruzione
	 *
	 * @return true se l'istruzione e' eseguita e il gioco continua, false
	 *         altrimenti
	 */
	private boolean processaIstruzione(String istruzione) {
		abstractComando comandoDaEseguire;
		FabbricaDiComandi factory = new FabbricaComandiRiflessiva(interfaccia);
		comandoDaEseguire = factory.costruisciComando(istruzione, interfaccia);
		comandoDaEseguire.esegui(this.partita);
		if (this.partita.vinta())

			this.interfaccia.mostraMessaggio("Hai vinto!");
		if (!this.partita.giocatoreIsVivo())

			this.interfaccia.mostraMessaggio("Hai esaurito i CFU...");

		return this.partita.isFinita();
	}

	public static void main(String[] argc) {
		try {
			Scanner scanner = new Scanner(System.in);
			IO io = new IOConsole(scanner);
			labirinto labirinto = new LabirintoBuilder().addStanzaIniziale("Laboratorio Campus")
					.addStanzaVincente("Biblioteca").addAdiacenza("Laboratorio Campus", "Biblioteca", Direzione.ovest)
					.getLabirinto();
			DiaDia gioco = new DiaDia(io, labirinto);
			gioco.gioca();
		} catch (Exception e) {
			System.out.println("Errore!!!!!");
		}
	}

}
