package it.uniroma3.diadia.ambienti;


public class StanzaBloccata extends Stanza {

	private Direzione direzioneBlocked;
	private String attrezzoSblocca;

	public StanzaBloccata(String nome, Direzione direzioneBlocked, String attrezzoSblocca) {
		super(nome);
		this.direzioneBlocked = direzioneBlocked;
		this.attrezzoSblocca = attrezzoSblocca;
	}

	@Override
	public Stanza getStanzaAdiacente(Direzione direzione) {
		if (direzione.equals(direzioneBlocked) && !hasAttrezzo(attrezzoSblocca))
			return this;
		return  super.getStanzaAdiacente(direzione);
	}
	
	@Override 
	public String getDescrizione() {
		if(!hasAttrezzo(attrezzoSblocca))
			return("Se non è presente l'attrezzo " + attrezzoSblocca + " nella stanza essa è bloccata");
		return super.getDescrizione();
	}
	@Override
	public int hashCode() {
		return this.getClass().hashCode()+ super.hashCode()+this.attrezzoSblocca.hashCode()+this.direzioneBlocked.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) 
			return false;
		StanzaBloccata that = (StanzaBloccata) obj;
		return super.equals((Stanza)that) && 
				this.attrezzoSblocca.equals(that.attrezzoSblocca) && 
				this.direzioneBlocked.equals(that.direzioneBlocked);
	}
}
