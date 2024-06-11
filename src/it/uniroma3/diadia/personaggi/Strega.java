package it.uniroma3.diadia.personaggi;

import java.util.Map;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Direzione;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Strega extends AbstractPersonaggio {

	private static final String MESSAGGIO_SPOSTAMENTO_NEGATIVO = "Hai sbagliato a non salutarmi maleducato, nella stanza con meno attrezzi "
			+ "verrai spostato";
	private static final String MESSAGGIO_SPOSTAMENTO_POSITIVO = "Hai fatto bene a salutarmi nella stanza con più attrezzi verrai spostato";

	public Strega(String nome, String presentazione) {
		super(nome, presentazione);
	}

	@Override
	public String agisci(Partita partita) {

		Map<Direzione, Stanza> stanzeAdiacenti = partita.getStanzaCorrente().getMapStanzeAdiacenti();

		Stanza stanzaMax = partita.getStanzaCorrente().getMapStanzeAdiacenti().values().iterator().next();
		Stanza stanzaMin = partita.getStanzaCorrente().getMapStanzeAdiacenti().values().iterator().next();

		for (Stanza s : stanzeAdiacenti.values()) {
			if (s.getAttrezzi().size() > stanzaMax.getAttrezzi().size())
				stanzaMax = s;
			if (s.getAttrezzi().size() < stanzaMin.getAttrezzi().size())
				stanzaMin = s;
		}

		if (this.haSalutato()) {
			partita.setStanzaCorrente(stanzaMax);
			return MESSAGGIO_SPOSTAMENTO_POSITIVO;
		} else
			partita.setStanzaCorrente(stanzaMin);
		return MESSAGGIO_SPOSTAMENTO_NEGATIVO;
	}

	@Override
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		return "HAHAHAHAHAHAHAHAHAHAHHAAHAHAHA";
	}

}