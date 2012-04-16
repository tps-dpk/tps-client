package de.wbh.projektarbeit.tps.model;

import java.io.Serializable;

public class Kunde implements Serializable {
	private static final long serialVersionUID = -5603981118192539580L;

	private String name;

	private String strasse;

	private String hausnummer;

	private String plz;

	private String ort;

	private String telefonnummer;

	public Kunde() {
	}

	public Kunde(String pName, String pStrasse, String pHausnummer,
			String pPlz, String pOrt, String pTelefonnummer) {
		name = pName;
		strasse = pStrasse;
		hausnummer = pHausnummer;
		plz = pPlz;
		ort = pOrt;
		telefonnummer = pTelefonnummer;
	}

	public String getName() {
		return name;
	}

	public void setName(String pName) {
		name = pName;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String pStrasse) {
		strasse = pStrasse;
	}

	public String getHausnummer() {
		return hausnummer;
	}

	public void setHausnummer(String pHausnummer) {
		hausnummer = pHausnummer;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String pPlz) {
		plz = pPlz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String pOrt) {
		ort = pOrt;
	}

	public String getTelefonnummer() {
		return telefonnummer;
	}

	public void setTelefonnummer(String pTelefonnummer) {
		telefonnummer = pTelefonnummer;
	}
}
