package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoSaluta extends abstractComando {

	private IO io;

	@Override
	public void esegui(Partita partita) {
		if (partita.getStanzaCorrente().getPersonaggio() == null)
			io.mostraMessaggio("Non c'è nessun personaggio nella stanza");
		else
			io.mostraMessaggio(partita.getStanzaCorrente().getPersonaggio().saluta());

	}

	@Override
	public String getNome() {
		return "saluta";
	}

}
