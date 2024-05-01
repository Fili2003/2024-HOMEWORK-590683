package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoFine implements Comando {

	private IO interfaccia;
	private String nome;

	public ComandoFine(IO interfaccia) {
		this.interfaccia = interfaccia;
	}

	@Override
	public void esegui(Partita partita) {
		this.interfaccia.mostraMessaggio("Grazie di aver giocato!");
	}

	@Override
	public void setParametro(String parametro) {
	}

	@Override
	public String getParametro() {
		return null;
	}

	@Override
	public String getNome() {
		return nome;
	}

}
