package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoFine extends abstractComando {

	private IO interfaccia;
	private String nome;

	public ComandoFine(IO interfaccia) {
		this.interfaccia = interfaccia;
	}

	public void esegui(Partita partita) {
		this.interfaccia.mostraMessaggio("Grazie di aver giocato!");
	}
	
	public String getNome() {
		return nome;
	}

}
