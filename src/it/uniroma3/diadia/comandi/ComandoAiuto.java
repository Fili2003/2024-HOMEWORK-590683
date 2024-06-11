package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.DiaDia;
import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoAiuto extends abstractComando{
	
	private IO interfaccia;
	private String nome;

	public ComandoAiuto(IO interfaccia, String nome) {
		this.interfaccia=interfaccia;
		this.nome=nome;
	}
	
	public void esegui(Partita partita) {
		for (int i = 0; i < DiaDia.elencoComandi.length; i++)
			this.interfaccia.mostraMessaggio(DiaDia.elencoComandi[i] + " ");
		this.interfaccia.mostraMessaggio(""); 
	}

	
	public String getNome() {
		return nome;
	}
}
