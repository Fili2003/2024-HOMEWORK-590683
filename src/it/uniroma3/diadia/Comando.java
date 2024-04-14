package it.uniroma3.diadia;
 
import java.util.Scanner;

/**
 * Questa classe modella un comando.
 * Un comando consiste al piu' di due parole:
 * il nome del comando ed un parametro
 * su cui si applica il comando.
 * (Ad es. alla riga digitata dall'utente "vai nord"
 *  corrisponde un comando di nome "vai" e parametro "nord").
 *
 * @author  docente di POO
 * @version base
 */

public class Comando {

    private String nome;
    private String parametro;

    public Comando(String istruzione) {
		Scanner scannerDiParole = new Scanner(istruzione);

		// prima parola: nome del comando
		if (scannerDiParole.hasNext()) // da falso all'inizio e non va bene perche invoca su scanner di parole che ha stringa vuota 
			//this.nome ha null perchè inizializzata cosi perchè nullpointerexpection--> per risolvere-->
			this.nome = scannerDiParole.next(); 

		// seconda parola: eventuale parametro// dove all'inizio non entra
		if (scannerDiParole.hasNext()) 
			this.parametro = scannerDiParole.next();
		//scannerDiParole.close();
		
    }

    public String getNome() {
        return this.nome;
    }

    public String getParametro() {
        return this.parametro;
    }

    public boolean sconosciuto() {
        return (this.nome == null);
    }
}