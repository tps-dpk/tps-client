package de.wbh.projektarbeit.tps.model;

import java.io.Serializable;
import java.util.Date;

public class Arbeitsauftrag implements Serializable {
	private static final long serialVersionUID = -5084587265100786052L;

	private long nummer;

	private Date von;

	private Date bis;

	private String beschreibung;

	private Kunde kunde;

	public Arbeitsauftrag() {
	}

	public Arbeitsauftrag(long pNummer, Date pVon, Date pBis,
			String pBeschreibung, Kunde pKunde) {
		nummer = pNummer;
		von = pVon;
		bis = pBis;
		beschreibung = pBeschreibung;
		kunde = pKunde;
	}

	public long getNummer() {
		return nummer;
	}

	public void setNummer(long pNummer) {
		nummer = pNummer;
	}

	public Date getVon() {
		return von;
	}

	public void setVon(Date pVon) {
		von = pVon;
	}

	public Date getBis() {
		return bis;
	}

	public void setBis(Date pBis) {
		bis = pBis;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String pBeschreibung) {
		beschreibung = pBeschreibung;
	}

	public Kunde getKunde() {
		return kunde;
	}

	public void setKunde(Kunde pKunde) {
		kunde = pKunde;
	}
}
