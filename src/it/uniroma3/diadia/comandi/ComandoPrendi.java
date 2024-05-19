package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPrendi implements Comando {
	private IO interfaccia;
	private String nomeAttrezzo;
	private String nome;

	public ComandoPrendi(IO interfaccia, String nome) {
		this.interfaccia = interfaccia;
		this.nome = nome;
	}

	@Override
	public void esegui(Partita partita) {
		if (partita == null)
			return;
		if (nomeAttrezzo == null)
			this.interfaccia.mostraMessaggio("Cosa vuoi prendere?");
		else {
			if (partita.getStanzaCorrente().hasAttrezzo(nomeAttrezzo)) {
				Attrezzo a = partita.getStanzaCorrente().getAttrezzo(nomeAttrezzo);
				if (partita.getGiocatore().getBorsa().addAttrezzo(a)) {// se lo aggiungo alla borsa lo rimuovo dalla
																		// stanza
					partita.getStanzaCorrente().removeAttrezzo(a);
					this.interfaccia.mostraMessaggio(nomeAttrezzo + " aggiunto alla borsa");
				} else
					this.interfaccia.mostraMessaggio("Borsa piena");
			} else
				this.interfaccia.mostraMessaggio(nomeAttrezzo + " assente nella stanza");
		}
		this.interfaccia.mostraMessaggio(partita.getGiocatore().getBorsa().toString());
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
