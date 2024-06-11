package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Direzione;
import it.uniroma3.diadia.ambienti.Stanza;

public class ComandoVai extends abstractComando {
	private Direzione direzione;
	private IO interfaccia;
	private String nome;

	public ComandoVai(String nome, IO interfaccia) {
		this.nome = nome;
		this.interfaccia = interfaccia;
	}

	public void esegui(Partita partita) {
		if (partita == null)
			return;
		Stanza stanzaCorrente = partita.getStanzaCorrente();
		Stanza prossimaStanza = null;
		if (this.direzione == null) {
			this.interfaccia.mostraMessaggio("Dove vuoi andare ? Devi specificare una direzione");
			return;
		}
		prossimaStanza = stanzaCorrente.getStanzaAdiacente(this.direzione);
		if (prossimaStanza == null) {
			this.interfaccia.mostraMessaggio("Direzione inesistente"); // Dove voglio andare non ci sono uscite
			return;
		}
		partita.setStanzaCorrente(prossimaStanza); // Aggiorno la stanza corrente essendomi spostato
		int cfu = partita.getGiocatore().getCfu();
		partita.getGiocatore().setCfu(--cfu); // Invece qua prima decremento quindi aggiorno quindi modifico

		FabbricaDiComandiFisarmonica factory = new FabbricaDiComandiFisarmonica();
		abstractComando comandoGuarda = factory.costruisciComando("guarda", interfaccia);
		comandoGuarda.esegui(partita);

	}

	public String getNome() {
		return nome;
	}

	public void setParametro(Direzione parametro) {
		this.direzione = parametro;
	}

}