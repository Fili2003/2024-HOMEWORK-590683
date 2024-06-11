package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoGuarda extends abstractComando {

	private IO interfaccia;
	private String nome;
	
	public ComandoGuarda(IO interfaccia){
		this.interfaccia=interfaccia;
	} 
	
	public void esegui(Partita partita) {
		this.interfaccia.mostraMessaggio("\nTi trovi nella stanza: " +partita.getStanzaCorrente().getDescrizione());
		this.interfaccia.mostraMessaggio("Nella borsa hai:" +partita.getGiocatore().getBorsa().toString());
		this.interfaccia.mostraMessaggio("Cfu rimanenenti:" +partita.getGiocatore().getCfu()); 
	}

	public String getNome() {
		return nome;
	}
	
}
