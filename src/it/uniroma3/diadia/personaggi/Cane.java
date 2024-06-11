package it.uniroma3.diadia.personaggi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Cane extends AbstractPersonaggio {

	private static final String MESSAGGIO_CANE = "Hai sbagliato a fare il furbo con me addesso si tolgo un cfu";
	private static final String CIBO_PREFERITO = "osso";
	private static final String MESSAGIO_REGALO_ACCETTATO = "Questo è proprio il mio cibo preferito ora ti lascio questo mio dono: cacciavite";
	private static final String MESSAGIO_REGALO_RIFIUTATO = "Questo non è il mio cibo preferito ti meriti un bel morso";

	public Cane(String nome, String presentazione) {
		super(nome, presentazione);
	}

	@Override
	public String agisci(Partita partita) {
		partita.getGiocatore().setCfu(partita.getGiocatore().getCfu() - 1);
		return MESSAGGIO_CANE;
	}

	@Override
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		if (attrezzo.getNome().equals(CIBO_PREFERITO)) {
			partita.getStanzaCorrente().addAttrezzo(new Attrezzo("cacciavite", 1));
			return MESSAGIO_REGALO_ACCETTATO;
		}

		else {
			partita.getGiocatore().setCfu(partita.getGiocatore().getCfu() - 1);
			return MESSAGIO_REGALO_RIFIUTATO;
		}
	}

}
