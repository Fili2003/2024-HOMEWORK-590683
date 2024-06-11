package it.uniroma3.diadia;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.Properties;

public class Proprietà {

	private static final String DIADIA_PROPERTIES = "diadia.properties";
	private static final String PESO_MAX = "pesoMaxBorsa";
	private static final String CFU = "cfuIniziali";
	private static final String DIREZIONI_MAX = "direzioniMax";
	private static final String ATTREZZI_MAX = "attrezziMax";
	private static final String SOGLIA_MAGICA_DEFAULT = "sogliaMagicaDefault";
	private static Properties prop = null;

	public static int getCFU() {
		if (prop == null)
			carica();
		return Integer.parseInt(prop.getProperty(CFU));
	}

	public static int getPesoMax() {
		if (prop == null)
			carica();
		return Integer.parseInt(prop.getProperty(PESO_MAX));
	}

	public static int getDirezioniMax() {
		if (prop == null)
			carica();
		return Integer.parseInt(prop.getProperty(DIREZIONI_MAX));
	}

	public static int getAttrezziMax() {
		if (prop == null)
			carica();
		return Integer.parseInt(prop.getProperty(ATTREZZI_MAX));
	}

	public static int getSogliaMagicaDefault() {
		if (prop == null)
			carica();
		return Integer.parseInt(prop.getProperty(SOGLIA_MAGICA_DEFAULT));
	}

	private static void carica() {
		prop = new Properties();
		try {
			FileInputStream input = new FileInputStream(DIADIA_PROPERTIES);
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
