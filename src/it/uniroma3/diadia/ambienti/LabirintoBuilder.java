package it.uniroma3.diadia.ambienti;

import java.util.HashMap;
import java.util.Map;

import it.uniroma3.diadia.attrezzi.Attrezzo;

public class LabirintoBuilder extends labirinto {

	private Map<String, Stanza> stanze;
	private Stanza stanzaIniziale;
	private Stanza stanzaVincente;
	private Attrezzo attrezzoAggiungere;
	private Stanza ultimaStanzaAggiunta;

	public LabirintoBuilder() {
		this.stanzaIniziale = null;
		this.stanzaVincente = null;
		this.ultimaStanzaAggiunta = null;
		this.stanze = new HashMap<String,Stanza>();
		super.setStanzaIniziale(stanzaIniziale);
		super.setStanzaVincente(stanzaVincente);
	}

	public LabirintoBuilder addStanzaIniziale(String iniziale) {
		if (iniziale != null) {
			stanzaIniziale = new Stanza(iniziale);
			setStanzaIniziale(stanzaIniziale);
			stanze.put(iniziale, stanzaIniziale);
			ultimaStanzaAggiunta(stanzaIniziale);
		}
		return this;
	}

	public LabirintoBuilder addStanzaVincente(String vincente) {
		if (vincente != null) {
			stanzaVincente = new Stanza(vincente);
			setStanzaVincente(stanzaVincente);
			stanze.put(vincente, stanzaVincente);
			ultimaStanzaAggiunta(stanzaVincente);
		}
		return this;
	}

	public LabirintoBuilder addAttrezzo(String nomeAttrezzo, int peso) {
		if (nomeAttrezzo != null) {
			attrezzoAggiungere = new Attrezzo(nomeAttrezzo, peso);
			if (ultimaStanzaAggiunta == null)
				return this;
			ultimaStanzaAggiunta.addAttrezzo(attrezzoAggiungere);
		}
		return this;
	}

	public LabirintoBuilder addAdiacenza(String stanzaCorrente, String stanzaAdiacente, String direzione) {
		if (stanze.get(stanzaCorrente) != null && stanze.get(stanzaAdiacente) != null) {
			Stanza s1 = stanze.get(stanzaCorrente);
			Stanza s2 = stanze.get(stanzaAdiacente);
			s1.impostaStanzaAdiacente(direzione, s2);
		}
		return this;
	}

	public LabirintoBuilder addStanza(String nomeStanza) {
		if (nomeStanza != null) {
			Stanza stanza = new Stanza(nomeStanza);
			stanze.put(nomeStanza, stanza);
			ultimaStanzaAggiunta(stanza);
		}
		return this;
	}

	public LabirintoBuilder addStanzaMagica(String nomeStanza, int soglia) {
		if (nomeStanza != null) {
			Stanza stanzaMagica = new StanzaMagica(nomeStanza,soglia);
			stanze.put(nomeStanza, stanzaMagica);
			ultimaStanzaAggiunta(stanzaMagica);
		}
		return this;
	}

	public LabirintoBuilder addStanzaBloccata(String nomeStanza, String direzioneBloccata, String attrezzoSblocca) {
		if (nomeStanza != null && direzioneBloccata != null && attrezzoSblocca != null) {
			Stanza stanzaBloccata = new StanzaBloccata(nomeStanza, direzioneBloccata, attrezzoSblocca);
			stanze.put(nomeStanza, stanzaBloccata);
			ultimaStanzaAggiunta(stanzaBloccata);
		}
		return this;
	}

	public LabirintoBuilder addStanzaBuia(String nomeStanza, String attrezzoLuminoso) {
		if (nomeStanza != null && attrezzoLuminoso != null) {
			Stanza stanzaBuia = new StanzaBuia(nomeStanza, attrezzoLuminoso);
			stanze.put(nomeStanza, stanzaBuia);
			this.ultimaStanzaAggiunta(stanzaBuia);
		}
		return this;
	}

	public LabirintoBuilder ultimaStanzaAggiunta(Stanza stanza) {
		this.ultimaStanzaAggiunta = stanza;
		stanze.put(stanza.getNome(), stanza);
		return this;
	}

	public LabirintoBuilder getLabirinto() {
		return this;
	}

	public Map<String, Stanza> getStanze() {
		return this.stanze;
	}
}
