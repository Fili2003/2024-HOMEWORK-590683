package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoRegala extends abstractComando{

	private IO io;
	
	@Override
	public void esegui(Partita partita) {
		if(partita.getStanzaCorrente().getPersonaggio()==null) {
			if(partita.getGiocatore().getBorsa().hasAttrezzo(this.getParametro())) {
				io.mostraMessaggio(partita.getStanzaCorrente().getPersonaggio().riceviRegalo(partita.getGiocatore().getBorsa().getAttrezzo(this.getParametro()), partita));
				partita.getGiocatore().getBorsa().removeAttrezzo(this.getParametro());
			}
			else
				io.mostraMessaggio("Non è possibile regalarti" + this.getParametro());
		}
		else
			io.mostraMessaggio("Non c'è nessun personaggio nella stanza");
	}
		

	@Override
	public String getNome() {
		return "regala";
	}

}