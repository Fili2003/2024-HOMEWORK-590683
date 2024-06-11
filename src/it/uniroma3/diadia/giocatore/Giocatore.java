package it.uniroma3.diadia.giocatore;

import it.uniroma3.diadia.Proprietà;

public class Giocatore {

	static final private int CFU_INIZIALI = Proprietà.getCFU();

	private int cfu;
	private Borsa borsa;

// gli passo i cfu iniziali e una nuova borsa 
	public Giocatore() {
		this.cfu = CFU_INIZIALI;
		this.borsa = new Borsa();
	}

	public int getCfu() {
		return this.cfu;
	}

	public void setCfu(int cfu) {
		this.cfu = cfu;
	}

	public Borsa getBorsa() {
		return borsa;
	}

	public void setBorsa(Borsa borsa) {
		this.borsa = borsa;
	}

}
