package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoNonValido implements Comando {

	private IO interfaccia;
	private String nome;

	public ComandoNonValido(IO interfaccia) {
		this.interfaccia = interfaccia;
	}
 
	@Override
	public void esegui(Partita partita) {
		this.interfaccia.mostraMessaggio("Comando non valido !!");
	}

	@Override
	public void setParametro(String parametro) {
	}

	@Override
	public String getParametro() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNome() {
		return nome;
	}

}
