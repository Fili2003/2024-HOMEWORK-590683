package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPosa implements Comando {

	private String nomeAttrezzo;
	private IO interfaccia;
	private String nome;

	public ComandoPosa(String nome, IO interfaccia) {
		this.nome = nome; 
		this.interfaccia = interfaccia;
	}

	@Override
	public void esegui(Partita partita) {
		if (nomeAttrezzo == null) 
			this.interfaccia.mostraMessaggio("Nulla da posare");
		else {
			if (!partita.getGiocatore().getBorsa().hasAttrezzo(nomeAttrezzo))
				this.interfaccia.mostraMessaggio("Attrezzo non presenta nella borsa per posarlo");
			else {
				Attrezzo a = partita.getGiocatore().getBorsa().getAttrezzo(nomeAttrezzo);

				if (partita.getStanzaCorrente().addAttrezzo(a)) { // se posso aggiunge lo rimuovo
					partita.getGiocatore().getBorsa().removeAttrezzo(nomeAttrezzo);
					this.interfaccia.mostraMessaggio(nomeAttrezzo + " posato in " + partita.getStanzaCorrente());
				} else
					this.interfaccia.mostraMessaggio("Non è stato possibile aggiungere l'attrezzo");
			}
		}
	}

	@Override
	public void setParametro(String parametro) {
		this.nomeAttrezzo = parametro;
	}

	@Override
	public String getParametro() {
		return this.nomeAttrezzo;
	}

	@Override
	public String getNome() {
		return nome;
	}
}
