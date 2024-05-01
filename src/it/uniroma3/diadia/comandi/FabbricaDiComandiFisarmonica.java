package it.uniroma3.diadia.comandi;

import java.util.Scanner;

import it.uniroma3.diadia.IO;

public class FabbricaDiComandiFisarmonica implements FabbricaDiComandi {

	@Override
	public Comando costruisciComando (String istruzione) {
		Scanner scannerDiParole = new Scanner(istruzione);
		String nomeComando = null;
		String parametro = null;
		Comando comando = null;
		IO interfaccia = null;
		if (scannerDiParole.hasNext())
			nomeComando = scannerDiParole.next();// prima parola: nome del comando
		if (scannerDiParole.hasNext())
			parametro = scannerDiParole.next(); // seconda parola: eventuale param.

		
		if (nomeComando == null) 
			comando = new ComandoNonValido(interfaccia);
		else if (nomeComando.equals("vai"))
			comando = new ComandoVai(nomeComando,interfaccia);
		else if (nomeComando.equals("prendi"))
			comando = new ComandoPrendi(interfaccia,nomeComando);
		else if (nomeComando.equals("posa"))
			comando = new ComandoPosa(nomeComando, interfaccia);
		else if (nomeComando.equals("aiuto"))
			comando = new ComandoAiuto(interfaccia);
		else if (nomeComando.equals("fine"))
			comando = new ComandoFine(interfaccia);
		else if (nomeComando.equals("guarda"))
			comando = new ComandoGuarda(interfaccia);
		else
			comando = new ComandoNonValido(interfaccia);
		comando.setParametro(parametro);
		return comando;
	}
}