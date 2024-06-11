
package it.uniroma3.diadia;

import java.io.*;
import java.util.*;

import it.uniroma3.diadia.ambienti.Direzione;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.ambienti.StanzaBloccata;
import it.uniroma3.diadia.ambienti.StanzaBuia;
import it.uniroma3.diadia.ambienti.StanzaMagica;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;
import it.uniroma3.diadia.personaggi.Cane;
import it.uniroma3.diadia.personaggi.Mago;
import it.uniroma3.diadia.personaggi.Strega;

public class CaricatoreLabirinto {

	/* prefisso di una singola riga di testo contenente tutti i nomi delle stanze */
	private static final String STANZE_MARKER = "Stanze:";

	/* prefisso di una singola riga contenente il nome della stanza iniziale */
	private static final String STANZA_INIZIALE_MARKER = "Inizio:";

	/* prefisso della riga contenente il nome stanza vincente */
	private static final String STANZA_VINCENTE_MARKER = "Vincente:";

	private static final String STANZA_BUIA_MARKER = "Buia:";

	private static final String STANZA_MAGICA_MARKER = "Magica:";

	private static final String STANZA_BLOCCATA_MARKER = "Bloccata:";

	/*
	 * prefisso della riga contenente le specifiche degli attrezzi da collocare nel
	 * formato <nomeAttrezzo> <peso> <nomeStanza>
	 */
	private static final String ATTREZZI_MARKER = "Attrezzi:";

	private static final String PERSONAGGI_MAGO = "PersonaggioMago:";
	private static final String PERSONAGGI_STREGA = "PersonaggioStrega:";
	private static final String PERSONAGGI_CANE = "PersonaggioCane:";

	/*
	 * prefisso della riga contenente le specifiche dei collegamenti tra stanza nel
	 * formato <nomeStanzaDa> <direzione> <nomeStanzaA>
	 */
	private static final String USCITE_MARKER = "Uscite:";

	/*
	 * Esempio di un possibile file di specifica di un labirinto (vedi
	 * POO-26-eccezioni-file.pdf)
	 * 
	 * Stanze: biblioteca, N10, N11 Inizio: N10 Vincente: N11 Attrezzi: martello 10
	 * biblioteca, pinza 2 N10 Uscite: biblioteca nord N10, biblioteca sud N11
	 * 
	 */
	private LineNumberReader reader;

	private Map<String, Stanza> nome2stanza;

	private Stanza stanzaIniziale;
	private Stanza stanzaVincente;
//	private Stanza stanzaBuia;
//	private Stanza stanzaBloccata;
//	private Stanza stanzaMagica;
//	private AbstractPersonaggio mago;
//	private AbstractPersonaggio strega;
//	private AbstractPersonaggio cane;

	public CaricatoreLabirinto(String nomeFile) throws FileNotFoundException {
		this.nome2stanza = new HashMap<String, Stanza>();
		this.reader = new LineNumberReader(new FileReader(nomeFile));
	}

	public CaricatoreLabirinto(StringReader nomeFile) throws FileNotFoundException {
		this.nome2stanza = new HashMap<String, Stanza>();
		this.reader = new LineNumberReader((nomeFile));
	}

	public void carica() throws FormatoFileNonValidoException {
		try {
			this.leggiECreaStanze();
			this.leggiInizialeEvincente();
			this.leggiECollocaAttrezzi();
			this.leggiEImpostaUscite();
			this.leggiStanzaBuia();
			this.leggiStanzaMagica();
			this.leggiStanzaBloccata();
			this.leggiECreaMaghi();
			this.leggiECreaCani();
			this.leggiECreaStreghe();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

	}

	private String leggiRigaCheCominciaPer(String marker) throws FormatoFileNonValidoException {
		try {
			String riga = this.reader.readLine();
			check(riga.startsWith(marker), "era attesa una riga che cominciasse per " + marker);
			return riga.substring(marker.length());
		} catch (IOException e) {
			throw new FormatoFileNonValidoException(e.getMessage());
		}
	}

	private void leggiECreaStanze() throws FormatoFileNonValidoException {
		String nomiStanze = this.leggiRigaCheCominciaPer(STANZE_MARKER);
		for (String nomeStanza : separaStringheAlleVirgole(nomiStanze)) {
			Stanza stanza = new Stanza(nomeStanza);
			this.nome2stanza.put(nomeStanza, stanza);
		}
	}

	private List<String> separaStringheAlleVirgole(String string) {
		List<String> result = new LinkedList<>();
		Scanner scanner = new Scanner(string);
		scanner.useDelimiter(",");
		try (Scanner scannerDiParole = scanner) {
			while (scannerDiParole.hasNext())
				result.add(scannerDiParole.next());
		}
		return result;
	}

	private void leggiInizialeEvincente() throws FormatoFileNonValidoException {
		String nomeStanzaIniziale = null;
		nomeStanzaIniziale = this.leggiRigaCheCominciaPer(STANZA_INIZIALE_MARKER);
		check(this.isStanzaValida(nomeStanzaIniziale), nomeStanzaIniziale + " non definita");
		String nomeStanzaVincente = this.leggiRigaCheCominciaPer(STANZA_VINCENTE_MARKER);
		check(this.isStanzaValida(nomeStanzaVincente), nomeStanzaVincente + " non definita");
		this.stanzaIniziale = this.nome2stanza.get(nomeStanzaIniziale);
		this.stanzaVincente = this.nome2stanza.get(nomeStanzaVincente);
	}

	private void leggiStanzaBuia() throws FormatoFileNonValidoException {
		String nomiStanzeBuie = this.leggiRigaCheCominciaPer(STANZA_BUIA_MARKER);
		for (String specifcheBuia : separaStringheAlleVirgole(nomiStanzeBuie)) {
			String nomeStanzaBuia = null;
			String nomeAttrezzo = null;
			try (Scanner scannerLinea = new Scanner(specifcheBuia)) {
				check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome di una stanza Buia."));
				nomeStanzaBuia = scannerLinea.next();
				check(this.isStanzaValida(nomeStanzaBuia), nomiStanzeBuie + " non definita");
				check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome di un attrezzo."));
				nomeAttrezzo = scannerLinea.next();
//				check(scannerLinea.hasNext(), msgTerminazionePrecoce(
//						"il nome della stanza in cui collocare l'attrezzo " + nomeAttrezzo + "."));
//				nomeStanze = scannerLinea.next();
			}
			Stanza stanzaBuia = this.nome2stanza.get(nomeStanzaBuia);
			stanzaBuia = new StanzaBuia(nomeStanzaBuia, nomeAttrezzo);
			nome2stanza.put(nomeStanzaBuia, stanzaBuia);
		}
	}

	private void leggiStanzaBloccata() throws FormatoFileNonValidoException {
		String nomiStanzeBloccate = null;
		nomiStanzeBloccate = this.leggiRigaCheCominciaPer(STANZA_BLOCCATA_MARKER);
		for (String specificaBloccata : separaStringheAlleVirgole(nomiStanzeBloccate)) {
			String nomeStanzaBloccata = null;
			String nomeAttrezzoSbloccante = null;
			String nomeDirezioneBloccata = null;
			try (Scanner scannerLinea = new Scanner(specificaBloccata)) {
				check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome di una stanza Bloccata."));
				specificaBloccata = scannerLinea.next();
				check(this.isStanzaValida(nomiStanzeBloccate), nomiStanzeBloccate + "non definita");
				check(scannerLinea.hasNext(), msgTerminazionePrecoce("una direzione bloccata."));
				nomeDirezioneBloccata = scannerLinea.next();
				check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome di un attrezzo."));
				nomeAttrezzoSbloccante = scannerLinea.next();
				check(scannerLinea.hasNext(), msgTerminazionePrecoce(
						"il nome della stanza in cui collocare l'attrezzo " + nomeAttrezzoSbloccante + "."));
				nomeStanzaBloccata = scannerLinea.next();
			}

			Stanza stanzaBloccata = this.nome2stanza.get(nomiStanzeBloccate);
			stanzaBloccata = new StanzaBloccata(nomeStanzaBloccata, this.nomi2direzione(nomeDirezioneBloccata),
					nomeAttrezzoSbloccante);
			nome2stanza.put(nomeStanzaBloccata, stanzaBloccata);
		}
	}

	private void leggiStanzaMagica() throws FormatoFileNonValidoException {
		String nomiStanzeMagiche = null;
		nomiStanzeMagiche = this.leggiRigaCheCominciaPer(STANZA_MAGICA_MARKER);
		for (String nomeStanzaMagica : separaStringheAlleVirgole(nomiStanzeMagiche)) {
			String nomeStanza = null;
			String sogliaMagica = null;
			int soglia = 3;
			try (Scanner scannerLinea = new Scanner(nomeStanzaMagica)) {
				check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome di una stanza Magica."));
				nomeStanzaMagica = scannerLinea.next();
				check(this.isStanzaValida(nomiStanzeMagiche), nomiStanzeMagiche + "non definita");
				if (scannerLinea.hasNext()) {
					sogliaMagica = scannerLinea.next();
					soglia = Integer.parseInt(sogliaMagica);
				}
//				check(scannerLinea.hasNext(), msgTerminazionePrecoce("la soglia magica."));
//				sogliaMagica = scannerLinea.next();
//				soglia = Integer.parseInt(sogliaMagica);
				check(scannerLinea.hasNext(), msgTerminazionePrecoce(
						"il nome della stanza in cui vi è la soglia magica " + sogliaMagica + "."));
				nomeStanza = scannerLinea.next();
			} catch (NumberFormatException e) {
				check(false, "Soglia magica " + sogliaMagica + " non valido,stanza magica creata con soglia di default"
						+ soglia);
			}
			Stanza stanzaMagica = this.nome2stanza.get(nomiStanzeMagiche);
			stanzaMagica = new StanzaMagica(nomeStanza, soglia);
			nome2stanza.put(nomeStanza, stanzaMagica);
		}
	}

	private void leggiECreaMaghi() throws FormatoFileNonValidoException {
		String nomiMaghi = this.leggiRigaCheCominciaPer(PERSONAGGI_MAGO);

		for (String specificaMago : separaStringheAlleVirgole(nomiMaghi)) {
			String nomeStanzaMago = null;
			String nomeMago = null;
			String presentazione = null;
			String nomeAttrezzo = null;
			String pesoAttrezzo = null;
			int peso = 0;
			try (Scanner scannerLinea = new Scanner(specificaMago)) {
				check(scannerLinea.hasNext(),
						msgTerminazionePrecoce("il nome di una stanza dove va posizioneto il mago"));
				nomeStanzaMago = scannerLinea.next();
				check(this.isStanzaValida(nomeStanzaMago), nomeStanzaMago + "non definita");
				check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome di un mago."));
				nomeMago = scannerLinea.next();
				check(scannerLinea.hasNext(), msgTerminazionePrecoce("la presentazione."));
				presentazione = scannerLinea.next();
				check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome di un attrezzo."));
				nomeAttrezzo = scannerLinea.next();
				check(scannerLinea.hasNext(), msgTerminazionePrecoce("il peso dell'attrezzo " + nomeAttrezzo + "."));
				pesoAttrezzo = scannerLinea.next();
				peso = Integer.parseInt(pesoAttrezzo);
			} catch (NumberFormatException e) {
				check(false,
						"Peso attrezzo " + nomeAttrezzo + " non valido, personaggio creato con peso di default" + peso);
			}

			AbstractPersonaggio mago = new Mago(nomeMago, presentazione, new Attrezzo(nomeAttrezzo, peso));
			nome2stanza.get(nomeStanzaMago).setPersonaggio(mago);
		}

	}

	private void leggiECreaStreghe() throws FormatoFileNonValidoException {
		String nomiStreghe = this.leggiRigaCheCominciaPer(PERSONAGGI_STREGA);

		for (String specificaStrega : separaStringheAlleVirgole(nomiStreghe)) {
			String nomeStanzaStrega = null;
			String nomeStrega = null;
			String presentazione = null;
			try (Scanner scannerLinea = new Scanner(specificaStrega)) {
				check(scannerLinea.hasNext(),
						msgTerminazionePrecoce("il nome di una stanza dove va posizioneto il mago"));
				nomeStanzaStrega = scannerLinea.next();
				check(this.isStanzaValida(nomeStanzaStrega), nomeStanzaStrega + "non definita");
				check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome di una strega."));
				nomeStrega = scannerLinea.next();
				check(scannerLinea.hasNext(), msgTerminazionePrecoce("la presentazione."));
				presentazione = scannerLinea.next();
			}
			AbstractPersonaggio strega = new Strega(nomeStrega, presentazione);
			nome2stanza.get(nomeStanzaStrega).setPersonaggio(strega);
		}

	}

	private void leggiECreaCani() throws FormatoFileNonValidoException {
		String nomiCane = this.leggiRigaCheCominciaPer(PERSONAGGI_CANE);

		for (String specificaCane : separaStringheAlleVirgole(nomiCane)) {
			String nomeStanzaCane = null;
			String nomeCane = null;
			String presentazione = null;
			try (Scanner scannerLinea = new Scanner(specificaCane)) {
				check(scannerLinea.hasNext(),
						msgTerminazionePrecoce("il nome di una stanza dove va posizioneto il cane"));
				nomeStanzaCane = scannerLinea.next();
				check(this.isStanzaValida(nomeStanzaCane), nomeStanzaCane + "non definita");
				check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome di una cane."));
				nomeCane = scannerLinea.next();
				check(scannerLinea.hasNext(), msgTerminazionePrecoce("la presentazione."));
				presentazione = scannerLinea.next();
			}
			AbstractPersonaggio cane = new Cane(nomeCane, presentazione);
			nome2stanza.get(nomeStanzaCane).setPersonaggio(cane);
		}

	}

	private void leggiECollocaAttrezzi() throws FormatoFileNonValidoException {
		String specificheAttrezzi = this.leggiRigaCheCominciaPer(ATTREZZI_MARKER);

		for (String specificaAttrezzo : separaStringheAlleVirgole(specificheAttrezzi)) {
			String nomeAttrezzo = null;
			String pesoAttrezzo = null;
			String nomeStanza = null;
			try (Scanner scannerLinea = new Scanner(specificaAttrezzo)) {
				check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome di un attrezzo."));
				nomeAttrezzo = scannerLinea.next();
				check(scannerLinea.hasNext(), msgTerminazionePrecoce("il peso dell'attrezzo " + nomeAttrezzo + "."));
				pesoAttrezzo = scannerLinea.next();
				check(scannerLinea.hasNext(), msgTerminazionePrecoce(
						"il nome della stanza in cui collocare l'attrezzo " + nomeAttrezzo + "."));
				nomeStanza = scannerLinea.next();
			}
			posaAttrezzo(nomeAttrezzo, pesoAttrezzo, nomeStanza);
		}
	}

	private void posaAttrezzo(String nomeAttrezzo, String pesoAttrezzo, String nomeStanza)
			throws FormatoFileNonValidoException {
		int peso;
		try {
			peso = Integer.parseInt(pesoAttrezzo);
			Attrezzo attrezzo = new Attrezzo(nomeAttrezzo, peso);
			check(isStanzaValida(nomeStanza),
					"Attrezzo " + nomeAttrezzo + " non collocabile: stanza " + nomeStanza + " inesistente");
			this.nome2stanza.get(nomeStanza).addAttrezzo(attrezzo);
		} catch (NumberFormatException e) {
			check(false, "Peso attrezzo " + nomeAttrezzo + " non valido");
		}
	}

	private boolean isStanzaValida(String nomeStanza) {
		return this.nome2stanza.containsKey(nomeStanza);
	}

	private void leggiEImpostaUscite() throws FormatoFileNonValidoException {
		String specificheUscite = this.leggiRigaCheCominciaPer(USCITE_MARKER);
		for (String specificheUscita : this.separaStringheAlleVirgole(specificheUscite)) {
			try (Scanner scannerDiLinea = new Scanner(specificheUscita)) {
				String stanzaPartenza = null;
				String dir = null;
				String stanzaDestinazione = null;
				while (scannerDiLinea.hasNext()) {
					check(scannerDiLinea.hasNext(), msgTerminazionePrecoce("le uscite di una stanza."));
					stanzaPartenza = scannerDiLinea.next();
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("la direzione di una uscita della stanza " + stanzaPartenza));
					dir = scannerDiLinea.next();
					check(scannerDiLinea.hasNext(), msgTerminazionePrecoce("la destinazione di una uscita della stanza "
							+ stanzaPartenza + " nella direzione " + dir));
					stanzaDestinazione = scannerDiLinea.next();

				}
				impostaUscita(stanzaPartenza, this.nomi2direzione(dir), stanzaDestinazione);
			}
		}
	}

	private String msgTerminazionePrecoce(String msg) {
		return "Terminazione precoce del file prima di leggere " + msg;
	}

	private void impostaUscita(String stanzaDa, Direzione dir, String nomeA) throws FormatoFileNonValidoException {
		check(isStanzaValida(stanzaDa), "Stanza di partenza sconosciuta " + dir);
		check(isStanzaValida(nomeA), "Stanza di destinazione sconosciuta " + dir);
		Stanza partenzaDa = this.nome2stanza.get(stanzaDa);
		Stanza arrivoA = this.nome2stanza.get(nomeA);
		partenzaDa.impostaStanzaAdiacente(dir, arrivoA);
	}

	final private void check(boolean condizioneCheDeveEsseraVera, String messaggioErrore)
			throws FormatoFileNonValidoException {
		if (!condizioneCheDeveEsseraVera)
			throw new FormatoFileNonValidoException(
					"Formato file non valido [" + this.reader.getLineNumber() + "] " + messaggioErrore);
	}

	public Stanza getStanzaIniziale() {
		return this.stanzaIniziale;
	}

	public Stanza getStanzaVincente() {
		return this.stanzaVincente;
	}

	public Map<String, Stanza> getStanze() {
		return this.nome2stanza;
	}

	public Direzione nomi2direzione(String d) {
		Direzione direzione = null;
		if (d.equals("Nord"))
			direzione = Direzione.nord;
		if (d.equals("Sud"))
			direzione = Direzione.sud;
		if (d.equals("Est"))
			direzione = Direzione.est;
		if (d.equals("Ovest"))
			direzione = Direzione.ovest;
		return direzione;
	}
}