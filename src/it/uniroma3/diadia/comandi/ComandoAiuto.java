package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.DiaDia;
import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoAiuto implements Comando{
	
	private IO interfaccia;
	private String nome;

	public ComandoAiuto(IO interfaccia) {
		this.interfaccia=interfaccia;
	}
	
	@Override 
	public void esegui(Partita partita) {
		for (int i = 0; i < DiaDia.elencoComandi.length; i++)
			this.interfaccia.mostraMessaggio(DiaDia.elencoComandi[i] + " ");
		this.interfaccia.mostraMessaggio("");
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
