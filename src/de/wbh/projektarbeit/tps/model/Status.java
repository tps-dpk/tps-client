package de.wbh.projektarbeit.tps.model;

public enum Status {
	NEU("neu"), ANGENOMMEN("angenommen"), ABGELEHNT("abgelehnt"), ABGESCHLOSSEN(
			"abgeschlossen");

	public String value;

	private Status(String pValue) {
		value = pValue;
	}
}
