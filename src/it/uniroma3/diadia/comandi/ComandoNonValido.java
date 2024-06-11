package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoNonValido extends abstractComando {

	private IO interfaccia;
	private String nome;

	public ComandoNonValido(IO interfaccia) {
		this.interfaccia = interfaccia;
	}
 
	
	public void esegui(Partita partita) {
		this.interfaccia.mostraMessaggio("Comando non valido !!");
	}

	public String getNome() {
		return nome;
	}
}
