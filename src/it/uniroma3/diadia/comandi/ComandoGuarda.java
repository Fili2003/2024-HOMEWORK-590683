package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoGuarda implements Comando {

	private IO interfaccia;
	private String nome;
	
	public ComandoGuarda(IO interfaccia){
		this.interfaccia=interfaccia;
	} 
	
	
	@Override
	public void esegui(Partita partita) {
		this.interfaccia.mostraMessaggio("Informazioni della stanza:\n" +partita.getStanzaCorrente().getDescrizione());
		this.interfaccia.mostraMessaggio("Nella borsa hai:" +partita.getGiocatore().getBorsa().toString());
		this.interfaccia.mostraMessaggio("Cfu rimanenenti:" +partita.getGiocatore().getCfu());
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
