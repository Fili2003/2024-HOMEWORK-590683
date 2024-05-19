package it.uniroma3.diadia.giocatore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Borsa {
	public final static int DEFAULT_PESO_MAX_BORSA = 10;
	private Map<String, Attrezzo> attrezzi;
	private int pesoMax;

	public Borsa() {
		this(DEFAULT_PESO_MAX_BORSA);
	}

	public Borsa(int pesoMax) {
		this.pesoMax = pesoMax;
		this.attrezzi = new HashMap<String, Attrezzo>();
	}

	// aggiunge attrezzo alla borsa max 10 elementi
	public boolean addAttrezzo(Attrezzo attrezzo) {
		if (attrezzo == null || this.getPeso() + attrezzo.getPeso() > this.getPesoMax())
			return false;
		this.attrezzi.put(attrezzo.getNome(), attrezzo);
		return true;
	}

	public int getPesoMax() {
		return pesoMax;
	}

	public Attrezzo getAttrezzo(String nomeAttrezzo) {
		return this.attrezzi.get(nomeAttrezzo);
	}

	public int getPeso() {
		int peso = 0;

		Collection<Attrezzo> collezioneAttrezzi = this.attrezzi.values();
		for (Attrezzo attrezzo : collezioneAttrezzi)
			if (attrezzo != null)
				peso += attrezzo.getPeso();

		return peso;
	}

	public boolean isEmpty() {
		return this.attrezzi.size() == 0;
	}

	public boolean hasAttrezzo(String nomeAttrezzo) {
		return this.attrezzi.containsKey(nomeAttrezzo);
	}

	public Attrezzo removeAttrezzo(String nomeAttrezzo) {
		Attrezzo a = null;
		if (nomeAttrezzo != null)
			a = this.attrezzi.remove(nomeAttrezzo);
		return a;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		if (!this.isEmpty()) {
			s.append("Contenuto borsa (" + this.getPeso() + "kg/" + this.getPesoMax() + "kg): ");
			
			s.append(getContenutoOrdinatoPerNome().toString()+ " ");
		} else
			s.append("Borsa vuota");
		return s.toString();
	}

	// facciamo ordinamento esterno migliore
	public List<Attrezzo> getContenutoOrdinatoPerPeso() {
		Collection<Attrezzo> listaAttrezzi = this.attrezzi.values();
		final List<Attrezzo> ordinatoPerPeso = new ArrayList<>(listaAttrezzi);
		final comparatorePerPeso cmp = new comparatorePerPeso();
		Collections.sort(ordinatoPerPeso, cmp);

		return ordinatoPerPeso;

	}

	public SortedSet<Attrezzo> getContenutoOrdinatoPerNome() {
		Collection<Attrezzo> listaAttrezzi = this.attrezzi.values();
		final SortedSet<Attrezzo> ordinataPerNome = new TreeSet<>(listaAttrezzi);

		return ordinataPerNome;
	}

	public SortedSet<Attrezzo> getSortedSetOrdinatoPerPeso() {
		Collection<Attrezzo> listaAttrezzi = this.attrezzi.values();
		final comparatorePerPeso cmp = new comparatorePerPeso();
		final SortedSet<Attrezzo> ordinatoPerPeso = new TreeSet<>(cmp);
		ordinatoPerPeso.addAll(listaAttrezzi);
		return ordinatoPerPeso;

	}

	public Map<Integer, Set<Attrezzo>> getContenutoRaggruppatoPerPeso() {
		Collection<Attrezzo> listaAttrezzi = this.attrezzi.values();
		final Map<Integer, Set<Attrezzo>> peso2attrezzi = new HashMap<>();
		for (Attrezzo attrezzo : listaAttrezzi) {
			if (peso2attrezzi.containsKey(attrezzo.getPeso())) {
				// questo attrezzo ha un peso che ho già visto
				// pesco il vecchio set con lo stesso peso e aggiungo il nuovo arrivato
				final Set<Attrezzo> stessoPeso = peso2attrezzi.get(attrezzo.getPeso()); // ho il set di tutti gli
																						// attrezzi visti prima con quel
																						// peso
				stessoPeso.add(attrezzo); // aggiungo oggetto nuovo al set
			} else {
				// questo attrezzo ha un peso mai visto prima
				// creo un nuovo set per ospitare tutti gli attrezzi contenuri e futuri con
				// questo peso
				final Set<Attrezzo> nuovoSet = new HashSet<>();
				peso2attrezzi.put(attrezzo.getPeso(), nuovoSet);
			}
		}
		return peso2attrezzi;
	}

}
