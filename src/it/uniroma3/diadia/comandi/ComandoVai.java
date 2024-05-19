package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Stanza;

public class ComandoVai implements Comando {
	private String direzione;
	private IO interfaccia;
	private String nome;

	public ComandoVai(String nome, IO interfaccia) {
		this.nome = nome;
		this.interfaccia=interfaccia;
	}

	@Override
	public void esegui(Partita partita) {
		if(partita == null)  
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
		Comando comandoGuarda = factory.costruisciComando("guarda", interfaccia);
		comandoGuarda.esegui(partita);
		
		
	}

	@Override
	public void setParametro(String parametro) {
		this.direzione = parametro; 
	}

	@Override
	public String getParametro() {
		return this.direzione;
	}

	@Override
	public String getNome() {
		return nome;
	}
}