package it.uniroma3.diadia.ambienti;


public class StanzaBuia extends Stanza {
	
	private String attrezzoLuminoso;
	
	public StanzaBuia(String nome,String attrezzoLuminoso) {
		super(nome);
		this.attrezzoLuminoso=attrezzoLuminoso;
	}
	public String getAttrezzoIlluminante() {
		return this.attrezzoLuminoso;
	}
	
	@Override
	public String getDescrizione(){
		
		if(!hasAttrezzo(attrezzoLuminoso))
			return "Qui c'è buio pesto";
		
		return super.getDescrizione();
	}
	@Override
	public int hashCode() {
		return this.getClass().hashCode()+super.hashCode()+ this.attrezzoLuminoso.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) 
			return false;
		StanzaBuia that = (StanzaBuia) obj;
		return super.equals((Stanza)that) && this.attrezzoLuminoso.equals(that.attrezzoLuminoso);
	}




	
}
